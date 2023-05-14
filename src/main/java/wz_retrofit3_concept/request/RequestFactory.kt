package wz_retrofit3_concept.request

import okhttp3.Headers
import okhttp3.MediaType
import wz_retrofit3_concept.Retrofit3
import wz_retrofit3_concept.delegates.MethodPathResult
import wz_retrofit3_concept.delegates.ParameterParserDelegate
import wz_retrofit3_concept.delegates.httpMethodAndPath.HttpMethodAndPathParserDelegate
import wz_retrofit3_concept.exceptions.throwMethodError
import wz_retrofit3_concept.parameter.ParameterHandler
import java.lang.reflect.Method
import java.lang.reflect.Type


class RequestFactory {

    companion object {

//        fun parseAnnotations(retrofit3: Retrofit3, method:Method): RequestFactory {
//            //TODO("Need to be implemented")
//        }

    }

    class Builder(val retrofit3: Retrofit3, val method:Method) {

        // Upper and lower characters, digits, underscores, and hyphens, starting with a character.
//        private val PARAM = "[a-zA-Z][a-zA-Z0-9_-]*"
//        private val PARAM_URL_REGEX = Pattern.compile("\\{($PARAM)\\}")
//        private val PARAM_NAME_REGEX = Pattern.compile(PARAM)

        val methodAnnotations: Array<Annotation> = method.annotations
        val parameterAnnotationsArray: Array<Array<Annotation>> = method.parameterAnnotations
        val parameterTypes: Array<Type> = method.genericParameterTypes

        var hasField = false
        private set

        var hasPart = false
        private set

        var hasBody = false
        private set

        var gotBody = false
            private set

        var gotField = false
            private set

        var gotPart = false
            private set

        var gotUrl = false
            private set

        var hasPath = false
        private set

        var gotPath = false
            private set

        var hasQuery = false
        private set

        var gotQuery = false
            private set

        var hasQueryName = false
        private set

        var gotQueryName = false
            private set

        var hasQueryMap = false
        private set

        var gotQueryMap = false
            private set

        var hasUrl = false
        private set

        var httpMethod:String = ""
        private set
        var isFormEncoded = false
        private set

        var isMultiPart = false
        private set

        var relativeUrl = ""
        private set

        var headers: Headers? = null
        private set

        var mediaType:MediaType? = null
        private set

        var relativeUrlParamNames:Set<String>? = null
        private set

        var parameterHandlers: ArrayList<ParameterHandler<*>> = ArrayList()

        fun build():RequestFactory? {

            println("method annotations size: ${methodAnnotations.size}")

            for (methodAnnotationFound in methodAnnotations) {
                println("method annotations found $methodAnnotationFound")
                val parseResult by HttpMethodAndPathParserDelegate(annotation = methodAnnotationFound, builder = this)
                mapData(result = parseResult)
            }

            println("http method: $httpMethod")
            if (httpMethod.isBlank())
                throwMethodError(method = method, message = "HTTP method annotation is required (e.g., @GET, @POST, etc.).")

            println("hasBody: $hasBody")
            if (!hasBody) {

                if (isMultiPart)
                    throwMethodError(method = method, message = "Error at request '$method' Multipart can only be specified on HTTP methods with request body (e.g., @POST).")

                if (isFormEncoded)
                    throwMethodError(method = method, message = "FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).")
            }

            val parameterParserResult by ParameterParserDelegate(builder = this)

            println("parameter parser result: ${parameterParserResult}")



            //val parameterCount: Int = parameterAnnotationsArray.size
            //parameterHandlers =  arrayOfNulls<ParameterHandler<*>>(parameterCount)

            //println("parameter count: ${parameterAnnotationsArray.size}")

//            for (annotationsFound in parameterAnnotationsArray) {
//
//                println("annotation found: $annotationsFound")
//
//            }




            for (index in parameterAnnotationsArray.indices) {
                println("parameter annotation index: $index")
                println("parameter annotation item: ${parameterAnnotationsArray[index]}")
            }


            return null
//            val parameterCount = parameterAnnotationsArray.size
//            parameterHandlers
//
//
//            for (annotationsFound in parameterAnnotationsArray) {
//
//
//
//            }

            /*
            val testArray = arrayOf(1,2,3,4,5,6,7,8,9,10)

    println("size: ${testArray.size}")
    for (i in testArray.indices) {
        println("index: $i -> value: ${testArray[i]}")
    }

            */

        }

        private fun mapData(result: MethodPathResult) {

            result.apply {

                httpMethod?.let {
                    this@Builder.httpMethod = it
                }

                hasBody?.let {
                    this@Builder.hasBody = it
                }

                relativeUrl?.let {
                    this@Builder.relativeUrl = it
                }

                relativeUrlParameters?.let {
                    this@Builder.relativeUrlParamNames = it
                }

                headers?.let {
                    this@Builder.headers = it
                }

                contentType?.let {
                    this@Builder.mediaType = it
                }

                isMultiPart?.let {
                    this@Builder.isMultiPart = it
                }

                isFormEncoded?.let {
                    this@Builder.isFormEncoded = it
                }
            }
        }
    }

}