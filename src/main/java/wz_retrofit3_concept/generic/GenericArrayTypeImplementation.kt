package wz_retrofit3_concept.generic

import wz_retrofit3_concept.extensions.customTypeEquals
import wz_retrofit3_concept.extensions.toStringName
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type

class GenericArrayTypeImplementation(private val componentType: Type): GenericArrayType {

    override fun getGenericComponentType(): Type = componentType

    override fun equals(o: Any?): Boolean = o is GenericArrayType && customTypeEquals(equalTo = o)

    override fun hashCode(): Int = componentType.hashCode()

    override fun toString(): String = "${this.toStringName()}[]"

}