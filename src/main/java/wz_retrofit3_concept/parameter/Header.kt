package wz_retrofit3_concept.parameter

import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.request.RequestBuilder

class Header<T>(private val name:String?, private val valueConverter: Converter<T, String>): ParameterHandler<T>() {

    override fun apply(builder: RequestBuilder, values: T?) {

        requireNotNull(name) { "name == null" }

        if (values == null) // Skip null values.
            return

        val headerValue = valueConverter.convert(values) ?: // Skip converted but null values.
        return

        builder.addHeader(name, headerValue)

    }
}