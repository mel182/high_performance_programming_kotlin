package collections

fun main() {
    // onEachIndexed
    listOf("b","c","d").onEachIndexed {
        index, item -> println("${index}:${item}")
    }
}