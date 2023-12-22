package regular_expression_matching

// The Regex.matchAt() and Regex.matchesAt() functions, introduced in 1.5.30,
// are now Stable. They provide a way to check whether a regular expression
// has an exact match at a particular position in a String or CharSequence.

// matchesAt() checks for a match and returns a boolean result.

fun main() {

    val testText = "Kotlin 1.7.0 is on its way!"
    // regular expression: one digit, dot, one digit, dot, one or more digits
    val versionRegex = "\\d[.]\\d[.]\\d".toRegex()

    testText.firstOrNull()?.let { textAtIndex0 ->
        println("Character at index 0: $textAtIndex0")
        println(versionRegex.matchesAt(input = testText, index = 0))
    }

    if (testText.length > 7) {
        val textAtIndex7 = testText[7]
        println("Character at index 7: $textAtIndex7")
        println(versionRegex.matchesAt(input = testText, index = 7))
    }

    // 'matchAt()' returns the match if it's found, or null if it isn't
    println(versionRegex.matchAt(testText,0)) // "null"
    println(versionRegex.matchAt(testText,7)?.value) // "1.7.0"
}