package Kotlin_1_5

//Starting from Kotlin 1.5.0, new char-to-code and char-to-digit conversion functions are Stable.
//These functions replace the current API functions, which were often confused with the similar
//string-to-Int conversion.
fun main() {

    // Functions to get the integer code of Char
    val codeTest = 't'.code
    println("code test: ${codeTest}")

    // To construct Char from the given code
    val charTest = Char(116)
    println("char construct int: ${charTest}")

    val uShortCharTest = Char(116u)
    println("char construct ushort: ${uShortCharTest}")

    val charDigit = '1'

    // To convert Char to the numeric value of the digit it represents
    val digitInt = charDigit.digitToInt()
    println("digit int extension: $digitInt")

    val digitIntOrNull = charDigit.digitToIntOrNull()
    println("digit int or null extension: $digitIntOrNull")

    // An extension function for Int to convert the non-negative single
    // digit it represents to the corresponding 'Char representation:
    val digitChar = digitInt.digitToChar()
    println("Digit to char: $digitChar")

    //IMPORTANT: The old conversion APIs, including 'Number.toChar()' with its
    //            implementations (all except 'Int.toChar()') and Char extensions
    //            for conversion to a numeric type, like 'Char.toInt()', are now deprecated
}