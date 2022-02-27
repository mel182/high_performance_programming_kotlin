package kotlin_1_6.splitting_regex_int_a_sequence

// The 'Regex.splitToSequence(CharSequence)' and 'CharSequence.splitToSequence(Regex)' functions
//  are promoted to Stable. They split the string around matches of the given regex, but return
//  the result as a Sequence so that all operations on this result are executed lazily:
fun main() {
    val colorText = "green, red, brown&blue, orange, pink&green"
    val regex = "[,\\s]".toRegex()
    val mixedColor1 = regex.splitToSequence(colorText).filter { it.contains('&') }.toList()
    println("| ------------- mixed color 1 ------------- |\n")
    println("mixed color 1: $mixedColor1")
    println("| ------------- mixed color 1 ------------- |\n")
    val mixedColor = regex.splitToSequence(colorText)
            .onEach { println(it) }
            .firstOrNull { it.contains('&') }
    println("mixed color 2: $mixedColor")
}