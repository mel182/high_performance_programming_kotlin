package wz_retrofit3_concept.parameter

import okhttp3.Headers
import okhttp3.RequestBody
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.request.RequestBuilder
import java.io.IOException
import java.lang.reflect.Method

class Part<T>(private val method:Method, private val parameter:Int, private val headers: Headers, private val converter: Converter<Any, RequestBody>?): ParameterHandler<T>() {

    override fun apply(builder: RequestBuilder, values: T?) {

        if (values == null)
            return

        val body = try {
            converter?.convert(values)
        }catch (e:IOException) {
            throwParameterError(method = method, parameter = parameter, message = "Unable to convert $values to RequestBody",e)
        }

        builder.addPart(headers = headers, body = body as RequestBody)
    }
}