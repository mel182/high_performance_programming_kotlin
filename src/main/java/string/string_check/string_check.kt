package string.string_check

fun main() {
    val test = "test"
    println(test.toBoolean())

    val test1 ="test"
    val test2 ="test"

    println(test1 == test2)
    println(test1.contentEquals(test2))
}