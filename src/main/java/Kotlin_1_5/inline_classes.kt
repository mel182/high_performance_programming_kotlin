package Kotlin_1_5

import kotlin.time.Duration.Companion.seconds

/*
 * Inline classes are a subset of value-based classes that only hold values.
 * You can use them as wrappers for a value of a certain type without the additional
 * overhead that comes from using memory allocations.
 */
fun main() {

    val test1 = TestValueClass("Melchior")
    println("Test1: $test1")

    greetAfterDuration(Duration2())

}

fun greetAfterDuration(duration:Duration2)
{
    println("Greet: ${duration.seconds()}")
}

// Inline classes can be declared with the value modifier before the name of the class
// The JVM backend also requires a special '@JvmInline' annotation.
// The 'inline' modifier is now deprecated with a warning.
// Note: 'data class' is a fully featured class with additional goodies.
//       'value class' is a named, unmodifiable tuple without identity.
// 'data class' is available today for production use, while 'value class' is a work
// in progress project.
@JvmInline
value class TestValueClass(val name:String)

// Another example
@JvmInline
value class Duration2(val value:Long = 2000L)
{
    fun minutes():Long = value
    fun seconds():Long = value * 1000L
}