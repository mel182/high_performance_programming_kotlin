package wz_retrofit3_concept.parameter

import okhttp3.Headers
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.lang.reflect.Method

class Headers(private val method:Method, private val parameter:Int): ParameterHandler<Headers>() {

    override fun apply(builder: RequestBuilder, values: Headers?) {

        if (values == null)
            throwParameterError(method = method, parameter = parameter, message = "Headers parameter must not be null.")

        builder.addHeaders(values!!)
    }
}