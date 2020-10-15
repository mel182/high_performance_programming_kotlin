package Kotlin_1_4.function_reference_default_argument

fun testFunction(i: Int = 0): String = "$i"

// Old implementation
fun apply(func: () -> String):String = func()

// New implementation
fun applyInt(value:Int,func: (Int) -> String): String = func(value)

fun main() {

    // Old implementation
    println(apply(::testFunction))

    // New implementation
    println(applyInt(12,::testFunction))
}