package wz_retrofit3_concept

import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.asResponseBody
import okio.Buffer
import java.io.IOException
import java.lang.reflect.*
import java.lang.reflect.Array
import kotlin.Annotation
import kotlin.Any
import kotlin.Boolean
import kotlin.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.Throws
import kotlin.require
import kotlin.requireNotNull


fun getParameterUpperBound(index: Int, type: ParameterizedType?): Type {
    requireNotNull(type) { "type == null" }
    val types = type.actualTypeArguments
    require(!(index < 0 || index >= types.size)) { "Index $index not in range [0,${types.size}) for $type" }
    val paramType = types[index]
    return if (paramType is WildcardType) {
        paramType.upperBounds[0]
    } else paramType
}

@Throws(IllegalArgumentException::class)
fun getRawType(type:Type?): Class<*> {

    requireNotNull(type) {"type == null"}

    return when(type) {
        is Class<*> -> type
        is ParameterizedType -> {
            val rawType = type.rawType
            if (rawType !is Class<*>)
                throw java.lang.IllegalArgumentException()

            return rawType
        }
        is GenericArrayType -> {
            val componentType = type.genericComponentType
            return Array.newInstance(getRawType(componentType),0).javaClass
        }
        is TypeVariable<*> -> Any::class.java
        is WildcardType -> getRawType(type.upperBounds[0])

        else -> throw java.lang.IllegalArgumentException("")
    }
}

fun getSystemPlatform(): String = System.getProperty("java.vm.name")

fun isAnnotationPresent(annotations: kotlin.Array<Annotation>, _class: Class<out Annotation>): Boolean {
    for (annotation in annotations) {
        if (_class.isInstance(annotation)) {
            return true
        }
    }
    return false
}

@Throws(IOException::class)
fun buffer(body: ResponseBody): ResponseBody? {
    val buffer = Buffer()
    body.source().readAll(buffer)
    return buffer.asResponseBody(body.contentType(), body.contentLength())
}

//fun resolve(context:Type, contextRawType:Class<*>, toResolve:Type): Type {
//
//    while (true) {
//
//        return if (toResolve is TypeVariable<*>) {
//
//
//        } else if (toResolve is Class<*> && toResolve.isArray) {
//
//        } else if (toResolve is GenericArrayType) {
//
//        } else if (toResolve is ParameterizedType) {
//
//        } else if (toResolve is WildcardType) {
//
//        } else {
//            toResolve
//        }
//    }
//
//
//}

//fun resolveVariableType()


