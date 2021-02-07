package collections

import java.util.*

// A linked list contains a group of nodes that represent a sequence.
// Each node, in turn, contains a link to the next node, as well as stored data:
// There are also doubly-linked lists, in which each node contains a link to the
// previous node in addition to the next:
// A linked list supports operations such as:
// - public E getFirst()
// - public E getLast()
// - public E get(int index)
// - public E set(int index, E element)
// - public void add(int index, E element)
// - public boolean addAll(int index, Collection<? extends E> c)
//
fun main() {

    val list = LinkedList<Int>()
    list.add(0) // public void add(int index, E element)
    list.add(23)
    val itemSet = list.set(1,1) // public E set(int index, E element)
    println("Item at index 1: $itemSet set to: 1")

    val itemAtIndex1 = list[1]
    println("Item at index 1: $itemAtIndex1") // public E get(int index)

    val additionalList = listOf(12,13,14,15)
    val succeed = list.addAll(additionalList) // public boolean addAll(int index, Collection<? extends E> c)

    if (succeed)
    {
        println("Additional items added!")
    } else {
        println("Failed to add additional items!")
    }

    val firstItem = list.first
    println("First item on the list: $firstItem")

    val lastItem = list.last
    println("Last item on the list: $lastItem")

    list.forEach {
        println("list item found: ${it}")
    }
}