package wz_retrofit3_concept.delegates

import com.custom.http.client.annotation.http.call_properties.*
import okhttp3.Headers
import okhttp3.Headers.Companion.headersOf
import okhttp3.HttpUrl
import okhttp3.MultipartBody
import okhttp3.RequestBody
import property_delegation.interfaces.ReadOnlyProperty
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.converter.requestBodyConverter
import wz_retrofit3_concept.exceptions.throwMethodError
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.extensions.boxIfPrimitive
import wz_retrofit3_concept.extensions.convertToString
import wz_retrofit3_concept.extensions.retrieveSupertype
import wz_retrofit3_concept.extensions.validateResolvableType
import wz_retrofit3_concept.getParameterUpperBound
import wz_retrofit3_concept.getRawType
import wz_retrofit3_concept.parameter.ParameterHandler
import wz_retrofit3_concept.parameter.RawPart
import wz_retrofit3_concept.parameter.RelativeUrl
import wz_retrofit3_concept.request.RequestFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.URI
import kotlin.coroutines.Continuation
import kotlin.reflect.KProperty


class ParameterParserDelegate(private val builder:RequestFactory.Builder): ReadOnlyProperty<Any?, ParameterParserResult> {

    private var isSuspendableFunction = false
    private var gotUrl = false
    private var gotQuery = false
    private var gotQueryName:Boolean? = null
    private var gotQueryMap:Boolean? = null
    private var gotField:Boolean? = null
    private var gotPath:Boolean? = null
    private var gotBody:Boolean? = null
    private var gotPart:Boolean? = null
    private var result: ParameterHandler<*>? = null
    private val parameterHandlers: ArrayList<ParameterHandler<*>> = ArrayList()

    override fun getValue(thisRef: Any?, property: KProperty<*>): ParameterParserResult {

        parameterHandlers.apply {
            clear()

            for (annotationArrayIndex in builder.parameterAnnotationsArray.indices) {
                val annotationsFound = builder.parameterAnnotationsArray[annotationArrayIndex]
                parseParameter(parameterArrayItemIndex = annotationArrayIndex, parameterType = builder.parameterTypes[annotationArrayIndex], annotation = annotationsFound, allowContinuation = annotationArrayIndex == builder.parameterAnnotationsArray.lastIndex)?.let { parseResult ->
                    add(parseResult)
                }
            }
        }

        builder.apply {

            if (relativeUrl.isBlank() && !hasUrl)
                throwMethodError(method = method, message = "Missing either @${builder.httpMethod} URL or @Url parameter.")

            if (!isFormEncoded && !isMultiPart && !hasBody && gotBody)
                throwMethodError(method = method, message = "Non-body HTTP method cannot contain @Body.")

            if (isFormEncoded && !gotField)
                throwMethodError(method = method, message = "Form-encoded method must contain at least one @Field.")

            if (isMultiPart && !gotPart)
                throwMethodError(method = method, message = "Multipart method must contain at least one @Part.")

        }

        return ParameterParserResult(parameterHandlers = parameterHandlers, gotUrl = gotUrl, result = result, isSuspendableFunction = isSuspendableFunction)
    }


    private fun parseParameter(parameterArrayItemIndex:Int,parameterType: Type,annotation: Array<Annotation>, allowContinuation:Boolean): ParameterHandler<*>? {

        for (annotationIndex in annotation.indices) {

            val annotationFound = annotation[annotationIndex]

            println("annotation found: $annotationFound")

            val annotationAction = parseParameterAnnotation(parameterIndex = annotationIndex, parameterType = parameterType, annotations = annotation, annotation = annotationFound) ?: continue

            if (result != null) {
                throwParameterError(method = builder.method, parameter = annotationIndex, message = "Multiple Retrofit annotations found, only one allowed.")
            }

            result = annotationAction
        }

        if (result == null) {

            if (allowContinuation) {

                try {

                    if (getRawType(type = parameterType) == Continuation::class.java) {
                        isSuspendableFunction = true
                        return null
                    }

                }catch (_:NoClassDefFoundError){}

            }

            throwParameterError(method = builder.method, parameter = parameterArrayItemIndex, message = "No Retrofit annotation found.")
        }

        return result
    }


    private fun parseParameterAnnotation(parameterIndex:Int, parameterType: Type, annotations:Array<Annotation>, annotation:Annotation): ParameterHandler<*>? {

        return when(annotation) {

            is Url -> {

                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                if (builder.gotUrl)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Multiple @Url method annotations found.")

                if (builder.gotPath)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Path parameters may not be used with @Url.")

                if (builder.gotQuery)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Url parameter must not come after a @Query.")

                if (builder.gotQueryName)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Url parameter must not come after a @QueryName.")

                if (builder.gotQueryMap)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Url parameter must not come after a @QueryMap.")

                if (builder.relativeUrl.isNotBlank())
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Url cannot be used with @${builder.httpMethod} URL")

                gotUrl = true

                return when(parameterType) {

                    HttpUrl::class.java, String::class.java, URI::class.java -> {
                        RelativeUrl(method = builder.method, parameter = parameterIndex)
                    }

                    is Class<*> -> {

                        if (parameterType.name == "android.net.Uri") {
                            RelativeUrl(method = builder.method, parameter = parameterIndex)
                        } else {
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.")
                            null
                        }
                    }

                    else -> {
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.")
                        null
                    }
                }
            }

            is Path -> {

                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                if (builder.gotQuery)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Path parameter must not come after a @Query.")

                if (builder.gotQueryName)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Path parameter must not come after a @QueryName.")

                if (builder.gotQueryMap)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Path parameter must not come after a @QueryMap.")

                if (builder.gotUrl)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Path parameters may not be used with @Url.")

                if (builder.relativeUrl.isBlank())
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Path can only be used with relative url on @${builder.httpMethod}")

                gotPath = true

                val converter = parameterType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3) as Converter<*,String>
                wz_retrofit3_concept.parameter.Path(method = builder.method, parameter = parameterIndex, name = annotation.value, valueConverter = converter, encoded = annotation.encoded)
            }

            is Query -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                val rawParameterType = getRawType(parameterType)
                gotQuery = true

                return if (Iterable::class.java.isAssignableFrom(rawParameterType)) {

                    if (parameterType !is ParameterizedType)
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "${rawParameterType::class.java.simpleName} must include generic type (e.g., ${rawParameterType::class.java.simpleName}<string>)")

                    val parameterizedType = parameterType as ParameterizedType
                    val iterableType = getParameterUpperBound(index = 0, type = parameterizedType)
                    val converter = iterableType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    wz_retrofit3_concept.parameter.Query(name = annotation.value, valueConverter = converter, encoded = annotation.encoded).iterable()
                } else if (rawParameterType.isArray) {
                    val arrayComponentType = rawParameterType.componentType.boxIfPrimitive()
                    val converter = arrayComponentType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    wz_retrofit3_concept.parameter.Query(name = annotation.value, valueConverter = converter, encoded = annotation.encoded).array()
                } else {
                    val converter = parameterType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    wz_retrofit3_concept.parameter.Query(name = annotation.value, valueConverter = converter, encoded = annotation.encoded)
                }
            }

            is QueryName -> {
                val rawParameterType = getRawType(parameterType)
                gotQueryName = true
                return if (Iterable::class.java.isAssignableFrom(rawParameterType)) {
                    if (parameterType !is ParameterizedType)
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "${rawParameterType::class.java.simpleName} must include generic type (e.g., ${rawParameterType::class.java.simpleName}<string>)")

                    val parameterizedType = parameterType as ParameterizedType
                    val iterableType = getParameterUpperBound(index = 0, type = parameterizedType)
                    val converter = iterableType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    wz_retrofit3_concept.parameter.QueryName(nameConverter = converter, encoded = annotation.encoded).iterable()
                } else if (rawParameterType.isArray){
                    val arrayComponentType = rawParameterType.componentType.boxIfPrimitive()
                    val converter = arrayComponentType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    wz_retrofit3_concept.parameter.QueryName(nameConverter = converter, encoded = annotation.encoded).array()
                } else {
                    val converter = parameterType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    wz_retrofit3_concept.parameter.QueryName(nameConverter = converter, encoded = annotation.encoded)
                }
            }

            is QueryMap -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)
                val rawParameterType = getRawType(parameterType)
                gotQueryMap = true
                if (!Map::class.java.isAssignableFrom(rawParameterType))
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@QueryMap parameter type must be Map.")

                val mapType = parameterType.retrieveSupertype(contextRawType = rawParameterType, supertype = Map::class.java)
                if (mapType !is ParameterizedType) {
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Map must include generic types (e.g., Map<String, String>)")
                }

                val parameterizedType = mapType as ParameterizedType
                val keyType = getParameterUpperBound(index = 0, type = parameterizedType)
                if (keyType != String::class.java)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@QueryMap keys must be of type String: $keyType")

                val valueType = getParameterUpperBound(index = 1, type = parameterizedType)
                val valueConverter = valueType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)

                return wz_retrofit3_concept.parameter.QueryMap(method = builder.method, parameter = parameterIndex, valueConverter = valueConverter, encoded = annotation.encoded)
            }

            is Header -> {

                val rawParameterType = getRawType(parameterType)
                if (Iterable::class.java.isAssignableFrom(rawParameterType)) {

                    if (parameterType !is ParameterizedType)
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "${rawParameterType::class.java.simpleName} must include generic type (e.g., ${rawParameterType::class.java.simpleName}<String>)")

                    val parameterizedType = parameterType as ParameterizedType
                    val iterableType = getParameterUpperBound(index = 0, type = parameterizedType)
                    val valueConverter = iterableType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    return wz_retrofit3_concept.parameter.Header(name = annotation.value, valueConverter = valueConverter).iterable()

                } else if (rawParameterType.isArray) {
                    val arrayComponentType = rawParameterType.boxIfPrimitive()
                    val converter = arrayComponentType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    return wz_retrofit3_concept.parameter.Header(name = annotation.value, valueConverter = converter).array()
                } else {
                    val valueConverter = parameterType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    return wz_retrofit3_concept.parameter.Header(name = annotation.value, valueConverter = valueConverter)
                }

            }

            is HeaderMap -> {

                if (parameterType == Headers::class.java) {
                    return wz_retrofit3_concept.parameter.Headers(method = builder.method, parameter = parameterIndex)
                }

                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)
                val rawParameterType = getRawType(parameterType)
                if (!Map::class.java.isAssignableFrom(rawParameterType)) {
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@HeaderMap parameter type must be Map.")
                }

                val mapType = parameterType.retrieveSupertype(contextRawType = rawParameterType, supertype = Map::class.java)
                if (mapType is ParameterizedType) {
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Map must include generic types (e.g., Map<String, String>)")
                }

                val parameterizedType = mapType as ParameterizedType
                val keyType = getParameterUpperBound(index = 0, type = parameterizedType)
                if(String::class.java != keyType) {
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@HeaderMap keys must be of type String: $keyType")
                }

                val valueType = getParameterUpperBound(index = 1, type = parameterizedType)
                val valueConverter = valueType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)

                return wz_retrofit3_concept.parameter.HeaderMap(method = builder.method, parameter = parameterIndex, valueConverter = valueConverter)
            }

            is Field -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                if (!builder.isFormEncoded)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Field parameters can only be used with form encoding.")

                gotField = true

                val rawParameterType = getRawType(type = parameterType)
                if (Iterable::class.java.isAssignableFrom(rawParameterType)) {

                    if (parameterType !is ParameterizedType) {
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "${rawParameterType.getSimpleName()} must include generic type (e.g., ${rawParameterType.getSimpleName()}<String>)")
                    }
                    val parameterized = parameterType as ParameterizedType
                    val iterableType = getParameterUpperBound(index = 0, type = parameterized)
                    val converter = iterableType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    return wz_retrofit3_concept.parameter.Field(name = annotation.value, valueConverter = converter, encoded = annotation.encoded)
                } else if (rawParameterType.isArray) {
                    val arrayComponentType = rawParameterType.boxIfPrimitive()
                    val converter = arrayComponentType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    return wz_retrofit3_concept.parameter.Field(name = annotation.value, valueConverter = converter, encoded = annotation.encoded)
                } else {
                    val converter = parameterType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)
                    wz_retrofit3_concept.parameter.Field(name = annotation.value, valueConverter = converter, encoded = annotation.encoded)
                }
            }

            is FieldMap -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                if (!builder.isFormEncoded)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@FieldMap parameters can only be used with form encoding.")

                val rawParameterType = getRawType(type = parameterType)
                if (!Map::class.java.isAssignableFrom(rawParameterType))
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@FieldMap parameter type must be Map.")

                val mapType = parameterType.retrieveSupertype(contextRawType = rawParameterType, supertype = Map::class.java)
                if (mapType !is ParameterizedType)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Map must include generic types (e.g., Map<String, String>)")

                val parameterized = mapType as ParameterizedType
                val keyType = getParameterUpperBound(index = 0, type = parameterized)
                if (keyType != String::class.java){
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@FieldMap keys must be of type String: $keyType")
                }

                val valueType = getParameterUpperBound(index = 1, type = parameterized)
                val valueConverter = valueType.convertToString<Any>(annotations = annotations, retrofit = builder.retrofit3)

                gotField = true

                return wz_retrofit3_concept.parameter.FieldMap(method = builder.method, parameter = parameterIndex, valueConverter = valueConverter, encoded = annotation.encoded)
            }

            is Part -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                if (!builder.isMultiPart)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Part parameters can only be used with multipart encoding.")

                gotPart = true
                val rawParameterType = getRawType(type = parameterType)
                return if (annotation.value.isEmpty()) {

                    if (Iterable::class.java.isAssignableFrom(rawParameterType)) {
                        if (parameterType !is ParameterizedType) {
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "${rawParameterType::class.java.simpleName} must include generic type (e.g., ${rawParameterType::class.java.simpleName}<String>)")
                        }

                        val parameterizedType = parameterType as ParameterizedType
                        val iterableType = getParameterUpperBound(index = 0, type = parameterizedType)
                        if (!MultipartBody.Part::class.java.isAssignableFrom(getRawType(iterableType))) {
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Part annotation must supply a name or use MultipartBody.Part parameter type.")
                        }

                        RawPart.INSTANCE.iterable()
                    } else if (rawParameterType.isArray) {

                        val arrayComponentType = rawParameterType.componentType
                        if (!MultipartBody.Part::class.java.isAssignableFrom(arrayComponentType)) {
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Part annotation must supply a name or use MultipartBody.Part parameter type.")
                        }
                        RawPart.INSTANCE.array()
                    } else if (MultipartBody.Part::class.java.isAssignableFrom(rawParameterType)) {
                        RawPart.INSTANCE
                    } else {
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Part annotation must supply a name or use MultipartBody.Part parameter type.")
                        null
                    }
                } else {

                    val headers = headersOf("Content-Disposition",
                            "form-data; name=\"${annotation.value}\"",
                            "Content-Transfer-Encoding",
                            (annotation.value as Part).encoding
                    )

                    if (Iterable::class.java.isAssignableFrom(rawParameterType)) {

                        if (parameterType !is ParameterizedType) {
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "${rawParameterType::class.java.simpleName} must include generic type (e.g., ${rawParameterType::class.java.simpleName}<String>)")
                        }

                        val parameterizedType = parameterType as ParameterizedType
                        val iterableType = getParameterUpperBound(index = 0, type = parameterizedType)

                        if (MultipartBody.Part::class.java.isAssignableFrom(getRawType(iterableType))) {
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.")
                        }

                        val converter = requestBodyConverter<Any>(type = iterableType, parameterAnnotations = annotations, methodAnnotations = builder.methodAnnotations, converterFactories = builder.retrofit3.converterFactories ,retrofit = builder.retrofit3)
                        wz_retrofit3_concept.parameter.Part<Any>(method = builder.method, parameter = parameterIndex, headers = headers, converter = converter).iterable()
                    } else if (rawParameterType.isArray) {

                        val arrayComponentType = rawParameterType.boxIfPrimitive()
                        if (MultipartBody.Part::class.java.isAssignableFrom(arrayComponentType))
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.")

                        val converter = requestBodyConverter<Any>(type = arrayComponentType, parameterAnnotations = annotations, methodAnnotations = builder.methodAnnotations, converterFactories = builder.retrofit3.converterFactories ,retrofit = builder.retrofit3)
                        wz_retrofit3_concept.parameter.Part<Any>(method = builder.method, parameter = parameterIndex, headers = headers, converter = converter).array()
                    } else if (MultipartBody.Part::class.java.isAssignableFrom(rawParameterType)) {
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.")
                        null
                    } else {
                        val converter = requestBodyConverter<Any>(type = parameterType, parameterAnnotations = annotations, methodAnnotations = builder.methodAnnotations, converterFactories = builder.retrofit3.converterFactories ,retrofit = builder.retrofit3)
                        wz_retrofit3_concept.parameter.Part<Any>(method = builder.method, parameter = parameterIndex, headers = headers, converter = converter)
                    }
                }
            }

            is PartMap -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                if (!builder.isMultiPart)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@PartMap parameters can only be used with multipart encoding.")

                gotPart = true
                val rawParameterType = getRawType(parameterType)
                if (!Map::class.java.isAssignableFrom(rawParameterType))
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@PartMap parameter type must be Map.")

                val mapType = parameterType.retrieveSupertype(contextRawType = rawParameterType, supertype = Map::class.java)
                if (mapType !is ParameterizedType)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Map must include generic types (e.g., Map<String, String>)")

                val parameterizedType = mapType as ParameterizedType
                val keyType = getParameterUpperBound(index = 0, type = parameterizedType)
                if (keyType != String::class.java)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@PartMap keys must be of type String: $keyType")

                val valueType = getParameterUpperBound(index = 1, type = parameterizedType)
                if (MultipartBody.Part::class.java.isAssignableFrom(getRawType(valueType)))
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead.")

                val valueConverter = requestBodyConverter<Any>(type = valueType, parameterAnnotations = annotations, methodAnnotations = builder.methodAnnotations, converterFactories = builder.retrofit3.converterFactories ,retrofit = builder.retrofit3)
                return wz_retrofit3_concept.parameter.PartMap<Any>(method = builder.method, parameter = parameterIndex, transferEncoding = annotation.encoding).setValueConverter(_valueConverter = valueConverter)
            }

            is Body -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                if (builder.isFormEncoded || builder.isMultiPart)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Body parameters cannot be used with form or multi-part encoding.")

                if (builder.gotBody)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Multiple @Body method annotations found.")

                val converter: Converter<Any,RequestBody>? = try {
                    requestBodyConverter<Any>(type = parameterType, parameterAnnotations = annotations, methodAnnotations = builder.methodAnnotations, converterFactories = builder.retrofit3.converterFactories ,retrofit = builder.retrofit3)
                }catch (e:RuntimeException) {
                    // Wide exception range because factories are user code.
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Unable to create @Body converter for $parameterType")
                    null
                }

                gotBody = true
                return wz_retrofit3_concept.parameter.Body<Any>(method = builder.method, parameter = parameterIndex, converter = converter)
            }

            is Tag -> {
                parameterType.validateResolvableType(parameterIndex = parameterIndex, method = builder.method)

                val tagType: Class<*> = getRawType(parameterType)
                for (index in parameterIndex - 1 downTo 0) {
                    val otherHandler = parameterHandlers[index]
                    if (otherHandler is wz_retrofit3_concept.parameter.Tag<*> && otherHandler::class.java == tagType) {
                        throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Tag type ${tagType.name} is duplicate of parameter #${(index + 1)} and would always overwrite its value.")
                    }
                }

                return wz_retrofit3_concept.parameter.Tag<Any>().setClass(_class = tagType as Class<Any>)
            }

            else -> null // Not a Retrofit annotation.
        }
    }
}