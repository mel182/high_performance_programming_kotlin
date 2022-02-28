package collections

fun main() {
    // flatmap
    val list = listOf("hello","kot","lin","world")
    val kotlin = list.flatMapIndexed { index: Int, s: String ->
        println("flat map index: ${index} and s: ${s}")
        if (index in 1..2) s.toList() else emptyList()
    }
    println("flat map: ${kotlin}")
}