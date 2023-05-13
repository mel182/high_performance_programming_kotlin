package wz_retrofit3_concept.services

import wz_retrofit3_concept.Retrofit3
import wz_retrofit3_concept.exceptions.throwMethodError
import wz_retrofit3_concept.extensions.hasUnresolvableType
import wz_retrofit3_concept.request.RequestFactory
import java.lang.reflect.Method

abstract class ServiceMethod<T> {

    companion object {
        fun <T> parseAnnotations(retrofit: Retrofit3, method: Method): ServiceMethod<T>? {
//            val requestFactory: RequestFactory = RequestFactory.parseAnnotations(retrofit, method)
//            val returnType: Type = method.getGenericReturnType()
//            if (Utils.hasUnresolvableType(returnType)) {
//                throw methodError(
//                        method,
//                        "Method return type must not include a type variable or wildcard: %s",
//                        returnType)
//            }
//            if (returnType === Void.TYPE) {
//                throw methodError(method, "Service methods cannot return void.")
//            }
//            return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactory)

            return null

           TODO("Need to implemented")
        }

        fun parseAnnotations2(retrofit: Retrofit3, method: Method): ServiceMethod<*>? {
            println("parse annotations 2 -> retrofit: $retrofit")
            println("parse annotations 2 -> method: $method")
            println("parse annotations 2 -> return type: ${method.genericReturnType}")

            val returnType = method.genericReturnType

            if (returnType.hasUnresolvableType())
                throwMethodError(method = method, message = "Method return type must not include a type variable or wildcard: %s", args = arrayOf(returnType))

            if (returnType === Void.TYPE)
                throwMethodError(method = method, message = "Service methods cannot return void.")

            val requestFactory = RequestFactory.Builder(retrofit3 = retrofit, method = method).build()
            println("request factory: $requestFactory")


            println("return type: $returnType")


//            val requestFactory: RequestFactory = RequestFactory.parseAnnotations(retrofit, method)
//            val returnType: Type = method.getGenericReturnType()
//            if (Utils.hasUnresolvableType(returnType)) {
//                throw methodError(
//                        method,
//                        "Method return type must not include a type variable or wildcard: %s",
//                        returnType)
//            }
//            if (returnType === Void.TYPE) {
//                throw methodError(method, "Service methods cannot return void.")
//            }
//            return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactory)

            return null

            TODO("Need to implemented")
        }

    }

    abstract operator fun invoke(args: Array<Any>?): T?

}