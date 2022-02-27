package Kotlin_1_4.string

import java.lang.StringBuilder

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

    // Append line string builder
    val stringbuilderAppend = buildString{
        appendLine("Hello,")
        appendLine("world")
    }
    println(stringbuilderAppend)

    val test = "test"
    println(test.toBoolean())

    val test1 ="test"
    val test2 ="test"

    println(test1 == test2)
    println(test1.contentEquals(test2))

    val external_value = "external_value_"

    val endpointStringBuilderTest = StringBuilder("test")
    endpointStringBuilderTest.insertRange(0,external_value,0,external_value.length)

    println("endpoint url: ${endpointStringBuilderTest.appendLine()}")

}