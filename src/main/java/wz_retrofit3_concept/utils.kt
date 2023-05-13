package wz_retrofit3_concept

import java.lang.reflect.*
import java.lang.reflect.Array
import kotlin.Any
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