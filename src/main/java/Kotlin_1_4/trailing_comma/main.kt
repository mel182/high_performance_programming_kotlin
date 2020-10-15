package Kotlin_1_4.trailing_comma

fun main() {

    formatText("This is a String", upperCaseFirstLetter = false, '-')

    colors.forEach {
        println("Color found: ${it}")
    }
}

fun formatText(value:String = "",
               upperCaseFirstLetter:Boolean = true,
               wordSeperator: Char = ' ', // trailing comma
)
{
    println("Value: ${value}")
    println("Upper case first letter: ${upperCaseFirstLetter}")
    println("Word seperator: ${wordSeperator}")
}

val colors = listOf(
        "red",
        "green",
        "blue", // trailing comma
)
