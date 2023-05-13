package wz_retrofit3_concept.parameter

import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.lang.reflect.Method

class RelativeUrl(private val method: Method, private val parameter:Int): ParameterHandler<Any>() {
    override fun apply(builder: RequestBuilder, values: Any?) {
        values?.let {
            builder.setRelativeUrl(it)
        }?:run {
            throwParameterError(method = method, parameter = parameter, message = "@Url parameter is null.")
        }
    }

}