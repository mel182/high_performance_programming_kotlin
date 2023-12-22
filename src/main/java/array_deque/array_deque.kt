package array_deque

/**
 * ArrayDeque<T> is an implementation of a double-ended queue, which allows you to
 * add or remove elements both at the beginning or end of the queue. As such, ArrayDeque
 * also fills the role of both a Stack and Queue data structure in Kotlin.
 * Behind the scenes, ArrayDeque is realized using a resizable array that automatically
 * adjusts in size when required.
 */
fun main() {
    println("Deque array example")
    val deque = ArrayDeque(listOf(1, 2, 3))
    println("raw deque array: $deque") // [1, 2, 3]
    deque.addFirst(0)
    deque.addLast(4)
    println("modified deque array: $deque") // [0, 1, 2, 3, 4]
    println("")
    println("Print first: ${deque.first()}") // 0
    println("Print last: ${deque.last()}") // 4
    deque.removeFirst()
    deque.removeLast()
    println("Print deque array with first and last removed: $deque") // [1, 2, 3]
}