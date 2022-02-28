package inline_classes

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/*
 * Inline classes are a subset of value-based classes that only hold values.
 * You can use them as wrappers for a value of a certain type without the additional
 * overhead that comes from using memory allocations.
 */
fun main() {
    val test1 = TestValueClass("Melchior")
    println("Test1: $test1")
    println("Test1 length: ${test1.length}")

    greetAfterDuration(Duration2())

    println("Json encoding example:\n")
    val testJson = Json.encodeToString(NamedColor(Color(0),"black"))
    println("Json encoding result: $testJson") // {"color":0,"name":"black"}
    println("|----------------------|")
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
// Value classes can ONLY have one property and it must be immutable 'val'
@JvmInline
value class TestValueClass( val name:String)
{
    // Since value classes are wrapper classes, other properties are allowed
    // only if they don't have a backing field.
    val length : Int
        get() = name.length
}

// Another example
@JvmInline
value class Duration2(val value:Long = 2000L)
{
    fun minutes():Long = value
    fun seconds():Long = value * 1000L
}

@Serializable
@JvmInline
value class Color(val rgb:Int)

@Serializable
data class NamedColor(val color : Color, val name: String)