package collections

import java.util.*

// A stack is a data structure that's built on the last in, first out (LIFO) principle
// and allows you only to insert or delete an item at the top of the stack:

fun main() {
    println("Main function called!")

    val stack = Stack<Int>()
    stack.push(10)
    stack.push(12)

    val popUp = stack.pop() // Pop the up element from the stack
    println("Pop up item: ${popUp}") // 12
    println("stack size: ${stack.size}") // size = 1 (since the top element is removed)

    stack.forEach {
        println("Stack item found: $it") // 10
    }
}