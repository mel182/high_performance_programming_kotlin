package type_classes.stableTypeOf

import kotlin.reflect.typeOf

// Version 1.6.0 brings a Stable typeOf() function, closing one of the major roadmap items.
// Since 1.3.40, typeOf() was available on the JVM platform as an experimental API.
// Now you can use it in any Kotlin platform and get KType representation of any Kotlin
// type that the compiler can infer:
fun main() {

    val fromExplicitType = typeOf<Int>()
    val fromReifiedType = renderType<List<Int>>()

    println("from explicit type: $fromExplicitType")
    println("from reified type: $fromReifiedType")
}

inline fun <reified T> renderType(): String {
    val type = typeOf<T>()
    return type.toString()
}