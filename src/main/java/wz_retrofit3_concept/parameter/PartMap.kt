package wz_retrofit3_concept.parameter

import okhttp3.Headers.Companion.headersOf
import okhttp3.RequestBody
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.lang.reflect.Method

class PartMap<T>(private val method:Method, private val parameter: Int, private val transferEncoding: String): ParameterHandler<Map<String, T>>() {

    private var valueConverter: Converter<T, RequestBody>? = null

    fun setValueConverter(_valueConverter: Converter<T, RequestBody>?): PartMap<T> {
        this.valueConverter = _valueConverter
        return this
    }


    override fun apply(builder: RequestBuilder, values: Map<String, T>?) {

        if (values == null)
            throwParameterError(method = method, parameter = parameter, message = "Part map was null.")

        for (entry: Map.Entry<String?, T?> in values!!.entries) {
            val entryKey = entry.key ?: throwParameterError(method = method, parameter = parameter, message = "Part map contained null key.")
            val entryValue = entry.value ?: throwParameterError(method = method, parameter = parameter, message = "Part map contained null value for key '$entryKey'.")

            val headers = headersOf("Content-Disposition",
                    "form-data; name=\"$entryKey\"",
                    "Content-Transfer-Encoding",
                    transferEncoding
            )
            valueConverter?.convert(entryValue as T)?.let {
                builder.addPart(headers = headers, body = it)
            }
        }
    }
}