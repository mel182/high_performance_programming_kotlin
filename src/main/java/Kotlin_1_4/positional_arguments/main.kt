package Kotlin_1_4.positional_arguments

fun main() {
    println("Main function called!")
    //In Kotlin 1.4, there is no such limitation – you can now specify a name for an argument in the middle of a set of positional arguments. Moreover, you can mix positional and named arguments any way you like, as long as they remain in the correct order.
    formatText("This is a String", upperCaseFirstLetter = false,'-')
}

fun formatText(value:String = "", upperCaseFirstLetter:Boolean = true, wordSeperator: Char = ' ')
{
    println("Value: ${value}")
    println("Upper case first letter: ${upperCaseFirstLetter}")
    println("Word seperator: ${wordSeperator}")
}