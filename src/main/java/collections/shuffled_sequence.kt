package collections

fun main() {
    // shuffled sequence
    val numbers = (0 until 50).asSequence()
    val result = numbers.map { it * 2 }.shuffled().take(5)
    println("Shuffle result: ${result.toList()}")
}