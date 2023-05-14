package wz_retrofit3_concept.wildcard

import wz_retrofit3_concept.extensions.customTypeEquals
import wz_retrofit3_concept.extensions.toStringName
import wz_retrofit3_concept.extensions.verifyIfNotPrimitive
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

class WildcardTypeImplementation(private val upperBounds: Array<Type>, private val lowerBounds: Array<Type>): WildcardType {


    private var upperBound: Type? = null
    private var lowerBound: Type? = null

    init {

        if (upperBounds.size != 1)
            throw IllegalArgumentException()

        if (lowerBounds.size > 1)
            throw IllegalArgumentException()

        if (lowerBounds.size == 1) {

            lowerBounds[0].verifyIfNotPrimitive()

            if (upperBounds[0] != Any::class.java)
                throw IllegalArgumentException()

            this.lowerBound = lowerBounds[0]
            this.upperBound = Any::class.java
        } else {
            upperBounds[0].let {
                it.verifyIfNotPrimitive()
                this.lowerBound = null
                this.upperBound = upperBounds[0]
            }
        }
    }

    override fun getUpperBounds(): Array<Type> = upperBounds

    override fun getLowerBounds(): Array<Type> = lowerBound?.let { arrayOf(it) }?:run { emptyArray() }

    override fun equals(other: Any?): Boolean = other is WildcardType && this.customTypeEquals(equalTo = other)

    override fun hashCode(): Int {
        // This equals Arrays.hashCode(getLowerBounds()) ^ Arrays.hashCode(getUpperBounds()).
        return (if (lowerBound != null) 31 + lowerBound.hashCode() else 1) xor 31 + upperBound.hashCode()
    }

    override fun toString(): String {

        if (lowerBound != null)
            return "? super ${lowerBound?.typeName}"

        if (upperBound == Any::class.java)
            return "?"

        return "? extends ${upperBound?.toStringName()}"
    }
}