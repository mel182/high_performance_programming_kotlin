package Kotlin_1_5

// Kotlin 1.5.0 introduces the new API for getting a character’s category according to Unicode in
// multiplatform projects. Several functions are now available in all the platforms and in the common code.
fun main() {

    // Functions for checking whether a char is a letter or a digit:
    val charList = listOf('a','1','+')

    val (letterOrDigitList, notLetterOrDigitList) = charList.partition { it.isLetterOrDigit() }
    println("Letter or digit list: $letterOrDigitList") // [a, 1]
    println("Not letter or digit list: $notLetterOrDigitList")

    val charValue = 'a'
    println("is digit: ${charValue.isDigit()}") // false
    println("is letter: ${charValue.isLetter()}") // true
    println("is letter or digit: ${charValue.isLetterOrDigit()}") // true

    // Functions for checking the case of a char:
    println("is lower case: ${charValue.isLowerCase()}") // true
    println("is upper case: ${charValue.isUpperCase()}") // false
    println("is lower case: ${charValue.isTitleCase()}") // false

    // Title case example:
    val titleCaseChar = 'ǅ'
    println("Title case char: ${titleCaseChar.isTitleCase()}") // true

    val demoCharList = listOf('ǅ', 'ǈ', 'ǋ', 'ǲ', '1', 'A', 'a', '+')
    val (titleCases, NotTitleCases) = demoCharList.partition { it.isTitleCase() }
    println("Title case: $titleCases") // [ǅ, ǈ, ǋ, ǲ]
    println("Not title case: $NotTitleCases") // [1, A, a, +]

    println("is defined: ${titleCaseChar.isDefined()}") // true

    println("is ISO control: ${titleCaseChar.isISOControl()}") // false
    // Note: iso control check whether a char is an ISO control character
}