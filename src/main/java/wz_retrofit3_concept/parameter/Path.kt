package wz_retrofit3_concept.parameter

import com.custom.http.client.constant.BLANK_STRING
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.lang.reflect.Method

class Path<T>(private val method: Method, private val parameter: Int, private val name:String?, private val valueConverter: Converter<T,String>, private val encoded:Boolean): ParameterHandler<T>() {

    override fun apply(builder: RequestBuilder, values: T?) {

        requireNotNull(name) { "name == null" }

        if (values == null)
            throwParameterError(method = method, parameter = parameter, message = "Path parameter \"$name\" value must not be null.")

        builder.addPathParams(name = name, value = valueConverter.convert(values!!) ?: BLANK_STRING, encoded = encoded)
    }

}