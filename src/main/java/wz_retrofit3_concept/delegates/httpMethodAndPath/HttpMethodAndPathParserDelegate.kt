package wz_retrofit3_concept.delegates.httpMethodAndPath

import com.custom.http.client.annotation.http.call_properties.FormUrlEncoded
import com.custom.http.client.annotation.http.call_properties.Headers
import com.custom.http.client.annotation.http.call_properties.Multipart
import property_delegation.interfaces.ReadOnlyProperty
import wz_retrofit3_concept.annotation.http.HTTP
import wz_retrofit3_concept.annotation.http.method.*
import wz_retrofit3_concept.delegates.HeaderParserDelegate
import wz_retrofit3_concept.delegates.MethodPathResult
import wz_retrofit3_concept.exceptions.throwMethodError
import wz_retrofit3_concept.extensions.asPathParameters
import wz_retrofit3_concept.regex.PARAM_URL_REGEX
import wz_retrofit3_concept.request.RequestFactory
import java.util.regex.Matcher
import kotlin.reflect.KProperty

class HttpMethodAndPathParserDelegate(private val annotation:Annotation, private val builder: RequestFactory.Builder): ReadOnlyProperty<Any?, MethodPathResult> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): MethodPathResult {

        return when(annotation) {
            is DELETE -> parseData(configData = HttpMethodConfig.Delete(_value = annotation.value))

            is GET -> parseData(configData = HttpMethodConfig.Get(_value = annotation.value))

            is HEAD -> parseData(configData = HttpMethodConfig.Head(_value = annotation.value))

            is PATCH -> parseData(configData = HttpMethodConfig.Path(_value = annotation.value))

            is POST -> parseData(configData = HttpMethodConfig.Post(_value = annotation.value))

            is PUT -> parseData(configData = HttpMethodConfig.Put(_value = annotation.value))

            is OPTIONS -> parseData(configData = HttpMethodConfig.Options(_value = annotation.value))

            is HTTP -> parseData(configData = HttpMethodConfig.Http(_httpMethod = annotation.method,_value = annotation.path, methodHasBody = annotation.hasBody))

            is Headers -> {
                val headerParseResult by HeaderParserDelegate(headers = listOf(*annotation.value), method = builder.method)
                return MethodPathResult(headers = headerParseResult.headers, contentType = headerParseResult.contentType)
            }
            is Multipart -> {

                if (builder.isFormEncoded)
                    throwMethodError(method = builder.method, message = "Only one encoding annotation is allowed.")

                return MethodPathResult(isMultiPart = true)
            }
            is FormUrlEncoded -> {

                if (builder.isMultiPart)
                    throwMethodError(method = builder.method, message = "Only one encoding annotation is allowed.")

                return MethodPathResult(isFormEncoded = true)
            }

            else -> {
                throwMethodError(method = builder.method, message = "Provide a valid HTTP method (e.g., @GET, @POST, etc.)")
                MethodPathResult()
            }
        }
    }

    private fun parseData(configData:HttpConfigWrapper): MethodPathResult {

        if (configData.value.isBlank())
            return MethodPathResult()

        // Get the relative URL path and existing query string, if present.
        val questionMarkRegexIndex = configData.value.indexOf('?')
        if (questionMarkRegexIndex != -1 && questionMarkRegexIndex < configData.value.length - 1) {
            // Ensure the query string does not have any named parameters.
            val queryParams = configData.value.substring(questionMarkRegexIndex + 1)
            val queryParamMatcher: Matcher = PARAM_URL_REGEX.matcher(queryParams)
            if (queryParamMatcher.find())
                throwMethodError(method = builder.method, message = "URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", args = arrayOf(queryParams))
        }

        return MethodPathResult(httpMethod = configData.httpMethod, hasBody = configData.hasBody, relativeUrl = configData.value, relativeUrlParameters = configData.value.asPathParameters())
    }
}