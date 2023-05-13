package wz_retrofit3_concept.parameter

import com.custom.http.client.constant.BLANK_STRING
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.lang.reflect.Method

class HeaderMap<T>(private val method:Method, private val parameter:Int, private val valueConverter: Converter<T,String>): ParameterHandler<Map<String, T>>() {

    override fun apply(builder: RequestBuilder, values: Map<String, T>?) {

        if (values == null)
            throwParameterError(method = method, parameter = parameter, message = "Header map was null.")

        for (entry: Map.Entry<String?, T?> in values!!.entries) {

            val headerName = entry.key ?: throwParameterError(method = method, parameter = parameter, message = "Header map contained null key.")
            val headerValue = entry.value ?: throwParameterError(method = method, parameter = parameter, message = "Header map contained null value for key '$headerName'.")

            builder.addHeader(name = "$headerName", value = valueConverter.convert(headerValue as T) ?: BLANK_STRING)
        }
    }
}