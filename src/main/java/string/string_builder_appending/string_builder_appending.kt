package string.string_builder_appending

fun main() {
    // Append line string builder
    val stringbuilderAppend = buildString{
        appendLine("Hello,")
        appendLine("world")
    }
    println(stringbuilderAppend)

    val external_value = "external_value_"

    val endpointStringBuilderTest = StringBuilder("test")
    endpointStringBuilderTest.insertRange(0,external_value,0,external_value.length)

    println("endpoint url: ${endpointStringBuilderTest.appendLine()}")
}