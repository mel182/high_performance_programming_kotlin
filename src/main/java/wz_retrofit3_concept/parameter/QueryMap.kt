package wz_retrofit3_concept.parameter

import com.custom.http.client.constant.BLANK_STRING
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.lang.reflect.Method

class QueryMap<T>(private val method:Method, private val parameter:Int, private val valueConverter: Converter<T,String>, private val encoded:Boolean): ParameterHandler<Map<String, T?>>() {


    override fun apply(builder: RequestBuilder, values: Map<String, T?>?) {

        if (values == null)
            throwParameterError(method = method, parameter = parameter, message = "Query map was null")

        for (entry: Map.Entry<String?, T?> in values!!.entries) {
            val entryKey = entry.key ?: throwParameterError(method = method, parameter = parameter, message = "Query map contained null key.")
            val entryValue = entry.value ?: throwParameterError(method = method, parameter = parameter, message = "Query map contained null value for key '$entryKey'.")

            val convertedEntryValue = valueConverter.convert(entryValue as T)
            if (convertedEntryValue == null)
                throwParameterError(method = method, parameter = parameter, message = "Query map value '$entryValue' converted to null by ${valueConverter::javaClass.name} for key '$entryKey'.")

            builder.addQueryParam(name = "$entryKey", value = convertedEntryValue, encoded = encoded)
        }
    }
}