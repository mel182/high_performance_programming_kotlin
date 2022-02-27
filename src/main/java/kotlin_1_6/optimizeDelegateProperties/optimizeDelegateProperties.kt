package kotlin_1_6.optimizeDelegateProperties

// We optimized the generated JVM bytecode by omitting the
// $delegate field and generating immediate access to the
// referenced property.
//
// Kotlin no longer generates the field content$delegate.
// Property accessors of the content variable invoke the impl
// variable directly, skipping the delegated property's getValue/setValue
// operators and thus avoiding the need for the property reference object
// of the KProperty type.
class Box<T> {
    private var impl: T? = null

    var content: T? by ::impl
}

fun main() {
    println(Box<String>().content)
}


