package Kotlin_1_5

import java.lang.Exception

// Two new functions introduce case-sensitive strict versions of the existing String?.toBoolean()
fun main() {

    val stringValue = "true".lowercase()

    // 'String.toBooleanStrict()' throws an exception for all inputs except the literals 'true' and 'false'.
    // 'String.toBooleanStrictOrNull()' returns null for all inputs except the literals 'true' and 'false'.
    // Note: It is case sensitive, it must be in de lower case form otherwise it will throw an exception

    println("boolean strict: ${stringValue.toBooleanStrict()}") // true
    println("boolean strict or null: ${stringValue.toBooleanStrictOrNull()}") // true

    try {
        val stringInvalidBoolean = "Kotlin"

        println("boolean strict or null: ${stringInvalidBoolean.toBooleanStrictOrNull()}") // null
        println("boolean strict: ${stringInvalidBoolean.toBooleanStrict()}") // Exception
    }catch (e:Exception)
    {
        println("Kotlin string value exception: ${e.message}")
    }
}