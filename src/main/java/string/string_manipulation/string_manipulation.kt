package string.string_manipulation

fun main() {
    // String manipulation
    val stringbuilder = StringBuilder("Bye Kotlin 1.3.72")
    println("string builder: ${stringbuilder}")
    stringbuilder.deleteRange(0,3)
    stringbuilder.insertRange(0,"Hello",0,5)
    stringbuilder.set(15,'4')
    stringbuilder.setRange(17,19,"0")
    stringbuilder.appendLine()

    println("string builder: ${stringbuilder}")
}