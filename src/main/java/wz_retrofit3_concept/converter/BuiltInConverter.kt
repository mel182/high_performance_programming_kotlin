package wz_retrofit3_concept.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import wz_retrofit3_concept.Retrofit3
import wz_retrofit3_concept.annotation.http.Streaming
import wz_retrofit3_concept.buffer
import wz_retrofit3_concept.isAnnotationPresent
import java.lang.reflect.Type

class BuiltInConverter: Converter.Factory() {

    private var checkForKotlinUnit = true


    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>?, retrofit: Retrofit3): Converter<ResponseBody, *>? {

        if (type == ResponseBody::class.java) {
            return if (isAnnotationPresent(annotations ?: emptyArray(), Streaming::class.java)) {
                StreamingResponseBodyConverter.INSTANCE
            } else {
                BufferingResponseBodyConverter.INSTANCE
            }
        }

        if (type == Unit::class.java) {
            return VoidResponseBodyConverter.INSTANCE
        }

        if (checkForKotlinUnit) {

            try {
                if (type == Unit::class.java)
                    return UnitResponseBodyConverter.INSTANCE
            } catch (ignored: NoClassDefFoundError) {
                checkForKotlinUnit = false
            }
        }

        return null
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit3): Converter<*, RequestBody>? {

        return getRawType(type)?.let {

            if (RequestBody::class.java.isAssignableFrom(it))
                return RequestBodyConverter.INSTANCE
            else
                null
        }?:run {
            null
        }
    }

    class VoidResponseBodyConverter: Converter<ResponseBody, Unit> {

        companion object {
            @JvmStatic
            val INSTANCE = VoidResponseBodyConverter()
        }

        override fun convert(value: ResponseBody): Unit? {
            value.close()
            return null
        }
    }

    class UnitResponseBodyConverter: Converter<ResponseBody, Unit> {

        companion object {
            @JvmStatic
            val INSTANCE = UnitResponseBodyConverter()
        }

        override fun convert(value: ResponseBody): Unit {
            value.close()
            return
        }
    }

    class RequestBodyConverter: Converter<RequestBody,RequestBody> {

        companion object {
            @JvmStatic
            val INSTANCE = RequestBodyConverter()
        }

        override fun convert(value: RequestBody): RequestBody = value
    }

    class StreamingResponseBodyConverter: Converter<ResponseBody, ResponseBody> {

        companion object {
            @JvmStatic
            val INSTANCE = StreamingResponseBodyConverter()
        }

        override fun convert(value: ResponseBody): ResponseBody = value
    }

    class BufferingResponseBodyConverter: Converter<ResponseBody,ResponseBody> {

        companion object {
            @JvmStatic
            val INSTANCE = BufferingResponseBodyConverter()
        }

        override fun convert(value: ResponseBody): ResponseBody? {
            value.use { value ->
                // Buffer the entire body to avoid future I/O.
                return buffer(body = value)
            }
        }
    }

    class ToStringConverter<T>: Converter<T, String> {

        override fun convert(value: T): String = value.toString()
    }
}