package wz_retrofit3_concept.extensions

import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType

fun Type?.hasUnresolvableType(): Boolean = this?.let { reflectType ->

    when(reflectType) {

        is Class<*> -> false

        is ParameterizedType -> {

            for (actualTypeArgumentFound in reflectType.actualTypeArguments) {
                if (actualTypeArgumentFound.hasUnresolvableType())
                    return true
            }

            false
        }

        is GenericArrayType -> reflectType.genericComponentType.hasUnresolvableType()

        is TypeVariable<*> -> true

        is WildcardType -> true

        else -> true
    }

}?: true