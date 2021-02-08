package collections

import java.util.*

// A queue represents the first in, first out (FIFO) principle.
// This means that after an element is added, it can be removed only after removing all
// other elements that were added before it.
// Unlike the stack data structure, you can manipulate items from both ends of a queue.
// A queue supports two operations—Enqueue, to insert element at the end and Dequeue, to remove it
//
fun main() {

    val names:Queue<String> = LinkedList()
    names.add("Melchior")
    names.add("Melchior1")
    names.add("Melchior2")
    names.add("Melchior3")

    // Performance benchmark 1
    val startTime1 = System.currentTimeMillis()
    names.forEach {
        println("Item found in queue: $it")
    }
    val endTime1 = System.currentTimeMillis()
    println("For each lambda duration: ${endTime1 - startTime1}")
    // ----------------------

    // Performance benchmark 2
    val startTime2 = System.currentTimeMillis()
    for (name in names) // this one is faster
    {
        println("Item found in queue: $name")
    }
    val endTime2 = System.currentTimeMillis()
    println("For each non-lambda duration: ${endTime2 - startTime2}")
    // ----------------------

    if (names.isNotEmpty())
    {
        println("queue remove example")
        println("queue: ${names}")
        val elementFound = names.element() // Melchior
        println("Element found: ${elementFound}")
        names.remove() // Remove the element found from the queue
        println("queue result: ${names}\n") // [Melchior1, Melchior2, Melchior3]
    }

    if (names.isNotEmpty())
    {
        println("queue poll example")
        println("queue: ${names}")
        val elementFound = names.element() // Melchior1
        println("Element found before poll: ${elementFound}")
        names.poll() // The poll() method is similar to remove() (dequeue operation),
                     // but it returns null if the Queue is empty instead of throwing an exception
        println("queue result after poll: ${names}\n") // [Melchior2, Melchior3]
    }

    if (names.isNotEmpty())
    {
        println("Queue contains, element() and peek() example")
        println("queue: ${names}")
        val elementFound = names.element() // if the Queue is empty, element() throws NoSuchElementException
                                           // while peek() returns null.
        println("Element found using element: ${elementFound}") // Melchior2
        println("Element found using peek: ${names.peek()}") // Melchior2
        names.poll() // The poll() method is similar to remove() (dequeue operation),
        // but it returns null if the Queue is empty instead of throwing an exception

        val melchior3 = "Melchior3"
        if (names.contains(melchior3))
            println("Queue contains $melchior3 at index: ${names.indexOf(melchior3)}") // 0

        println("queue result after poll: ${names}\n") // [Melchior3]
    }
}