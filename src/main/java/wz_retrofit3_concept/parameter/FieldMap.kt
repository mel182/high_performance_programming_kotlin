package wz_retrofit3_concept.parameter

import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.lang.reflect.Method

class FieldMap<T>(private val method:Method, private val parameter:Int, private val valueConverter: Converter<T,String>, private val encoded:Boolean): ParameterHandler<Map<String?, T?>>() {

    override fun apply(builder: RequestBuilder, values: Map<String?, T?>?) {

        if (values == null)
            throwParameterError(method = method, parameter = parameter, message = "Field map was null.")

        for (entry: Map.Entry<String?, T?> in values!!.entries) {
            val entryKey = entry.key ?: throwParameterError(method = method, parameter = parameter, message = "Field map contained null key.")
            val entryValue = entry.value ?: throwParameterError(method = method, parameter = parameter, message = "Field map contained null value for key '$entryKey'.")

            val fieldEntry = valueConverter.convert(entryValue as T)
            if (fieldEntry == null)
                throwParameterError(method = method, parameter = parameter, message = "Field map value '$entryValue' converted to null by ${valueConverter::class.java.name} for key '$entryKey'.")

            builder.addFormField(name = "$entryKey", value = fieldEntry!!, encoded = encoded)
        }
    }
}