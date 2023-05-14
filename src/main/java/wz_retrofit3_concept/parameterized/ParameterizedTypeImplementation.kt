package wz_retrofit3_concept.parameterized

import wz_retrofit3_concept.extensions.customTypeEquals
import wz_retrofit3_concept.extensions.toStringName
import wz_retrofit3_concept.extensions.verifyIfNotPrimitive
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class ParameterizedTypeImplementation(private val ownerType: Type?, private val rawType:Type, private val argumentTypes: Array<Type>): ParameterizedType {

    init {

        require(!(rawType is Class<*> && ownerType == null != ((rawType as Class<*>).enclosingClass == null)))

        for (typeArgumentFound in argumentTypes) {
            typeArgumentFound.verifyIfNotPrimitive()
        }
    }

    override fun getActualTypeArguments(): Array<Type> = argumentTypes.clone()

    override fun getRawType(): Type = rawType

    override fun getOwnerType(): Type? = ownerType

    override fun equals(other: Any?): Boolean = other is ParameterizedType && this.customTypeEquals(equalTo = other)

    override fun hashCode(): Int {
        return (argumentTypes.contentHashCode()
                xor rawType.hashCode()
                xor (ownerType?.hashCode() ?: 0))
    }

    override fun toString(): String {
        if (argumentTypes.isEmpty())
            return rawType.toStringName()

        return StringBuilder().apply {
            append(rawType.toStringName())
            append("<")
            append(argumentTypes[0].toStringName())
            for (typeArgumentFound in argumentTypes) {
                append(", ")
                append(typeArgumentFound.toStringName())
            }
            append(">")
        }.toString()
    }
}