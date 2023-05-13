package wz_retrofit3_concept.parameter

import com.custom.http.client.constant.BLANK_STRING
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.request.RequestBuilder

class QueryName<T>(private val nameConverter: Converter<T,String>, private val encoded:Boolean): ParameterHandler<T>() {

    override fun apply(builder: RequestBuilder, values: T?) {

        if (values == null) // Skip null values.
            return

        builder.addQueryParam(name = nameConverter.convert(values) ?: BLANK_STRING, value = null, encoded = encoded)
    }
}