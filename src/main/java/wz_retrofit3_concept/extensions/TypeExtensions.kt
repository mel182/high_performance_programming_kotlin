@file:Suppress("UNCHECKED_CAST")

package wz_retrofit3_concept.extensions

import wz_retrofit3_concept.Retrofit3
import wz_retrofit3_concept.converter.BuiltInConverter
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.exceptions.throwParameterError
import wz_retrofit3_concept.generic.GenericArrayTypeImplementation
import wz_retrofit3_concept.parameterized.ParameterizedTypeImplementation
import wz_retrofit3_concept.wildcard.WildcardTypeImplementation
import java.lang.reflect.*
import java.util.*

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


fun Type.validateResolvableType(parameterIndex:Int, method:Method) {

    if (hasUnresolvableType())
        throwParameterError(method = method, parameter = parameterIndex, message = "Parameter type must not include a type variable or wildcard: $this")
}

fun <T> Type.convertToString(annotations:Array<Annotation>, retrofit:Retrofit3): Converter<T, String> {

    for (converterFactoryFound in retrofit.converterFactories) {

        val converter = converterFactoryFound.stringConverter(type = this, annotations = annotations, retrofit = retrofit)
        if (converter != null)
            return converter as Converter<T, String>
    }

    return BuiltInConverter.ToStringConverter() // Nothing matched. Resort to default converter which just calls toString().
}

fun Type.resolveVariableType(contextRawType:Class<*>, unknown: TypeVariable<*>): Type {

    val declaredByRaw = unknown.getDeclaringClass() ?: return unknown // We can't reduce this further.
    val declaredBy = this.genericSuperType(rawType = contextRawType, toResolve = declaredByRaw)

    if (declaredBy is ParameterizedType) {
        val index = declaredByRaw.typeParameters.indexOf(element = unknown)
        return declaredBy.actualTypeArguments[index]
    }

    return unknown
}

fun TypeVariable<*>.getDeclaringClass(): Class<*>? = if (this.genericDeclaration is Class<*>) this.genericDeclaration as Class<*> else null

fun Type.genericSuperType(rawType: Class<*>, toResolve:Class<*>): Type {

    if (toResolve == rawType)
        return this

    if (toResolve.isInterface) {
        for (index in 0 until rawType.interfaces.size)
        {
            val interfaceFound = rawType.interfaces[index]
            val genericInterfaceFound = rawType.genericInterfaces[index]

            if (interfaceFound == toResolve) {
                return genericInterfaceFound
            } else if (toResolve.isAssignableFrom(interfaceFound)){
                return  genericInterfaceFound.genericSuperType(rawType = interfaceFound, toResolve = toResolve)
            }
        }
    }

    // Check our supertypes.
    if (!rawType.isInterface) {
        while (rawType != Any::class.java) {

            val rawSuperType = rawType.superclass
            if (rawSuperType == toResolve) {
                return rawType.genericSuperclass
            } else if (toResolve.isAssignableFrom(rawSuperType)) {
                return rawType.genericSuperclass.genericSuperType(rawType = rawSuperType, toResolve = toResolve)
            }
        }
    }

    return toResolve
}

fun Type.retrieveSupertype(contextRawType: Class<*>, supertype: Class<*>): Type {
    require(supertype.isAssignableFrom(contextRawType))
    return resolve(contextRawType = contextRawType, toResolve = supertype)
}


fun Type.resolve(contextRawType: Class<*>, toResolve:Type): Type {

    while (true) {

        if (toResolve is TypeVariable<*>) {
            val resolvedType = this.resolveVariableType(contextRawType = contextRawType, unknown = toResolve)

            if (resolvedType == toResolve)
                return resolvedType

        } else if (toResolve is Class<*> && toResolve.isArray) {

            val componentType = toResolve.componentType
            val newComponentType = this.resolve(contextRawType = contextRawType, toResolve = componentType)
            return if (componentType == newComponentType) toResolve else GenericArrayTypeImplementation(componentType = newComponentType)

        } else if (toResolve is GenericArrayType) {
            val componentType = toResolve.genericComponentType
            val newComponentType = this.resolve(contextRawType = contextRawType, toResolve = componentType)
            return if (componentType == newComponentType) toResolve else GenericArrayTypeImplementation(componentType = newComponentType)
        } else if (toResolve is ParameterizedType) {

            val ownerType = toResolve.ownerType
            val newOwnerType = this.resolve(contextRawType = contextRawType, toResolve = ownerType)
            var hasChanged = newOwnerType != ownerType
            val argumentTypeList:ArrayList<Type> = ArrayList()

            val args = toResolve.actualTypeArguments
            for (arg in args) {
                val resolveTypeArgument = this.resolve(contextRawType = contextRawType, toResolve = arg)
                if (resolveTypeArgument != arg) {
                    if (!hasChanged) {
                        argumentTypeList.addAll(args.clone())
                        hasChanged = true
                    }
                    argumentTypeList.add(resolveTypeArgument)
                }
            }

            return if (hasChanged)
                ParameterizedTypeImplementation(ownerType = newOwnerType, rawType = toResolve.rawType, argumentTypes = argumentTypeList.toTypedArray())
            else
                toResolve
        } else if (toResolve is WildcardType) {

            val lowerBound = toResolve.lowerBounds
            val upperBound = toResolve.upperBounds

            if (lowerBound.size == 1) {
                val resolvedLowerBound = this.resolve(contextRawType = contextRawType, toResolve = toResolve)
                if (resolvedLowerBound != lowerBound[0]) {
                    return WildcardTypeImplementation(upperBounds = arrayOf(Any::class.java), lowerBounds = lowerBound)
                }
            } else if (upperBound.size == 1) {
                val resolvedUpperBound = this.resolve(contextRawType = contextRawType, toResolve = upperBound[0])
                if (resolvedUpperBound != upperBound[0])
                    return WildcardTypeImplementation(upperBounds = upperBound, lowerBounds = emptyArray())
            }

            return toResolve

        } else {
            return toResolve
        }
    }
}

fun Type.customTypeEquals(equalTo:Type): Boolean {

    if (this == equalTo) { // Also handles (this == null && equalTo == null).
        return true
    } else if (this is Class<*>) {
        return this == equalTo
    } else if (this is ParameterizedType) {

        if(equalTo !is ParameterizedType)
            return false

        val parameterEqualTo = equalTo as ParameterizedType
        val ownerA = this.ownerType
        val ownerB = parameterEqualTo.ownerType
        return (ownerA == ownerB || ownerA == ownerB)
                && this.rawType == parameterEqualTo.rawType
                && Arrays.equals(this.actualTypeArguments, parameterEqualTo.actualTypeArguments)
    } else if (this is GenericArrayType) {

        if (equalTo !is GenericArrayType)
            return false

        return customTypeEquals(equalTo = equalTo.genericComponentType)
    } else if (this is WildcardType) {

        if (equalTo !is WildcardType)
            return false

        return Arrays.equals(this.upperBounds, equalTo.upperBounds) &&
               Arrays.equals(this.lowerBounds, equalTo.lowerBounds)
    } else if (this is TypeVariable<*>) {

        if (equalTo !is TypeVariable<*>)
            return false

        return this.genericDeclaration == equalTo.genericDeclaration &&
               this.name == equalTo.name
    } else {
        return false
    }
}

fun Type.toStringName(): String = if (this is Class<*>)
    this.name
else
    this.toString()

@Throws(IllegalArgumentException::class)
fun Type.verifyIfNotPrimitive() {
    require(this is Class<*> && isPrimitive)
}