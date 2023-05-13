package wz_retrofit3_concept.parameter

import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.request.RequestBuilder

class Field<T>(private val name:String?, private val valueConverter: Converter<T,String>, private val encoded:Boolean): ParameterHandler<T>() {

    override fun apply(builder: RequestBuilder, values: T?) {

        requireNotNull(name) { "name == null" }

        if (values == null) // Skip null values.
            return

        val fieldValue = valueConverter.convert(values) ?: return
        builder.addFormField(name = name, value = fieldValue, encoded = encoded)
    }
}