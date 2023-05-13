package wz_retrofit3_concept.delegates

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import property_delegation.interfaces.ReadOnlyProperty
import wz_retrofit3_concept.constant.CONTENT_TYPE
import wz_retrofit3_concept.exceptions.throwMethodError
import wz_retrofit3_concept.extensions.isContentType
import java.lang.reflect.Method
import kotlin.reflect.KProperty

class HeaderParserDelegate(private val headers: List<String>, private val method: Method): ReadOnlyProperty<Any?, HeaderParserResult> {


    private var contentType: MediaType? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): HeaderParserResult {

        if (headers.isEmpty())
            throwMethodError(method = method, message = "@Headers annotation is empty.")

        val parseResult = parseHeaderData()
        return HeaderParserResult(headers = parseResult, contentType = contentType)
    }

    private fun parseHeaderData(): okhttp3.Headers {

        return okhttp3.Headers.Builder().apply {

            for (headerItemFound in headers) {

                val colon = headerItemFound.indexOf(':')
                if (colon == -1 || colon == 0 || colon == headerItemFound.length - 1)
                    throwMethodError(method = method, message = "@Headers value must be in the form \"Name: Value\". Found: %s", args = arrayOf(headerItemFound))

                val headerName = headerItemFound.substring(0,colon)
                val headerValue = headerItemFound.substring(colon + 1).trim()

                if (headerName.isContentType()) {
                    try {
                        contentType = headerValue.toMediaType()
                    } catch (e:IllegalArgumentException) {
                        throwMethodError(method = method, message = "Malformed content type: %s", cause = e,args = arrayOf(headerItemFound))
                    }
                } else {
                    add(headerName, headerValue)
                }
            }
        }.build()
    }

}