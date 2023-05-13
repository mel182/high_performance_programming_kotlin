package wz_retrofit3_concept.delegates

import com.custom.http.client.annotation.http.call_properties.Url
import okhttp3.HttpUrl
import property_delegation.interfaces.ReadOnlyProperty
import wz_retrofit3_concept.exceptions.throwMethodError
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.getRawType
import wz_retrofit3_concept.parameter.ParameterHandler
import wz_retrofit3_concept.request.RequestFactory
import java.lang.reflect.Method
import java.lang.reflect.Type
import java.net.URI
import kotlin.coroutines.Continuation
import kotlin.reflect.KProperty

class ParameterParserDelegate(private val builder:RequestFactory.Builder, private val allowContinuation:Boolean): ReadOnlyProperty<Any?, ParameterParserResult> {

    private var isSuspendableFunction = false
    private var gotUrl = false
    private var result: ParameterHandler<*>? = null
    private val parameterHandlers: ArrayList<ParameterHandler<*>> = ArrayList()

    override fun getValue(thisRef: Any?, property: KProperty<*>): ParameterParserResult {

        parameterHandlers.apply {
            clear()

            for (annotationArrayIndex in builder.parameterAnnotationsArray.indices) {
                val annotationsFound = builder.parameterAnnotationsArray[annotationArrayIndex]
                parseParameter(parameterArrayItemIndex = annotationArrayIndex, parameterType = builder.parameterTypes[annotationArrayIndex], annotation = annotationsFound)?.let { parseResult ->
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


    private fun parseParameter(parameterArrayItemIndex:Int,parameterType: Type,annotation: Array<Annotation>): ParameterHandler<*>? {

        for (annotationIndex in annotation.indices) {

            val annotationFound = annotation[annotationIndex]

            val annotationAction = parseParameterAnnotation(parameterIndex = annotationIndex, parameterType = parameterType, annotation = annotationFound) ?: continue

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


    private fun parseParameterAnnotation(parameterIndex:Int, parameterType: Type, annotation:Annotation): ParameterHandler<*>? {

        val parseResult:ParameterHandler<*>? = null

        when(annotation) {

            is Url -> {

                if (builder.gotUrl)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "Multiple @Url method annotations found.")

                if (builder.gotPath)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Path parameters may not be used with @Url.")

                if (builder.gotQuery)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Url parameter must not come after a @Query.")

                if (builder.gotQuery)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Url parameter must not come after a @Query.")

                if (builder.gotQueryName)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Url parameter must not come after a @QueryName.")

                if (builder.gotQueryMap)
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "A @Url parameter must not come after a @QueryMap.")

                if (builder.relativeUrl.isNotBlank())
                    throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Url cannot be used with @${builder.httpMethod} URL")

                gotUrl = true

                when(parameterType) {

                    HttpUrl::class.java, String::class.java, URI::class.java -> {}

                    is Class<*> -> {

                        if (parameterType.name == "android.net.Uri") {
                            //ParameterHandler.Re

                        } else {
                            throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.")
                        }
                    }

                    else -> throwParameterError(method = builder.method, parameter = parameterIndex, message = "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.")
                }

            }

            else -> {}

        }

        return parseResult
    }


}