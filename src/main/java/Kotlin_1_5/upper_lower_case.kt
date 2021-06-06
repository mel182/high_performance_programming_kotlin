package Kotlin_1_5

//This release brings a new locale-agnostic API for uppercase/lowercase text conversion.
//It provides an alternative to the toLowerCase(), toUpperCase(), capitalize(), and decapitalize() API functions, which are locale-sensitive.
//The new API helps you avoid errors due to different locale settings.
//
//IMPORTANT: The old API functions are marked as deprecated and will be
//           removed in a future release.
fun main() {

    //Earlier versions
    val test12 = "test".toUpperCase()
    println("test12: ${test12}")

    // 1.5.0 alternative
    val test123 = "test".uppercase()
    println("test123: ${test123}")

    //Earlier versions
    val capTestString = "test".capitalize()
    println("capTestString1: ${capTestString}")

    // 1.5.0 alternative
    val capTestString2 = "test".replaceFirstChar { it.uppercase() }
    println("capTestString2: ${capTestString2}")

    //Earlier versions
    val deCapTestString = "Test".decapitalize()
    println("decapTestString1: ${deCapTestString}")

    // 1.5.0 alternative
    val deCapTestString2 = "test".replaceFirstChar { it.lowercase() }
    println("decapTestString2: ${deCapTestString2}")

    // Char examples

    // Upper case example
    // Earlier versions
    val charUpperTest = 't'.toUpperCase()
    println("char upper test: $charUpperTest")

    // 1.5.0 alternative
    val charUpperTest2 = 't'.uppercaseChar() // char
    val charUpperTest2_1 = 't'.uppercase() // string
    println("char upper test 2: $charUpperTest2")
    println("char upper test 2.1: $charUpperTest2_1")

    // Lower case example
    // Earlier versions
    val charLowerTest = 't'.toLowerCase()
    println("char lower test: $charLowerTest")

    // 1.5.0 alternative
    val charLowerTest2 = 't'.lowercaseChar() // char
    val charLowerTest2_1 = 't'.lowercase() // string
    println("char lower test 2: $charLowerTest2")
    println("char lower test 2.1: $charLowerTest2_1")

    // Title case example
    // Earlier versions
    val charTitleCaseTest = 't'.toTitleCase()
    println("char title example: $charTitleCaseTest")

    // 1.5.0 alternative
    val charTitleTest1 = 't'.titlecaseChar() // char
    val charTitleTest2 = 't'.titlecase() // string
    println("char title example: $charTitleTest1")
    println("char title example 2: $charTitleTest2")
}