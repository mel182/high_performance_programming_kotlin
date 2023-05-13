package wz_retrofit3_concept.parameter

import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.request.RequestBuilder

class Query<T>(private val name:String?, private val valueConverter: Converter<T,String>, private val encoded:Boolean): ParameterHandler<T>() {

    override fun apply(builder: RequestBuilder, values: T?) {

        requireNotNull(name) { "name == null" }

        if (values == null)
            return

        val queryValue = valueConverter.convert(values) ?: return

        builder.addQueryParam(name = name, value = queryValue, encoded = encoded)
    }
}