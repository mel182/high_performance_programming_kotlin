package collections

// An array stores multiple items of the same type in one fixed-size sequence.
// It's easy to access and modify any element of an array by referencing its index.
// Arrays are an efficient way of reducing the number of variables and grouping them by logic and type.
// In Java and Kotlin, a multidimensional array is an array of arrays; it can be used to represent matrices or some figures in space.
// You can create arrays with different depth levels, but it's hard to manage arrays with more than three levels of depth.
//
// An array supports operations such as:
// - get(index: Int): T
// - set(index: Int, value: T): Unit
//
fun main() {

    val array = arrayOf(1, 2, 3, 4)

    array[0] = 5 // set(index: Int, value: T): Unit
    val valueAtIndex0 = array[0] //get(index: Int): T
    println("Value at index 0: ${valueAtIndex0}")

    array.forEach {
        println("Item found: ${it}")
    }


    // |-------------- Kotlin 1.4 update --------------|
    //Arrays
    var language = ""
    val letters = arrayOf("k","o","t","l","i","n")
    val fileExt = letters.onEach {
        language += it
    }.filterNot { it in "aeuio" }.take(2)
            .joinToString(prefix = ".", separator = "")
    println(language)
    println(fileExt)

    letters.shuffle()
    println("Shuffle letters: ${letters.contentToString()}")

    letters.reverse(0,3)
    println("Letter reverse: ${letters.contentToString()}")

    letters.sortDescending(2,5)
    println("Sort descending: ${letters.contentToString()}")

    println(letters.contentToString())

    // ---------------

    val letters2 = arrayOf("k","o","t","l","i","n")
    val fileExt2 = letters.onEach {
        language += it
    }.filterNot { it in "aeuio" }.take(2)

    println("file extension: ${fileExt2}")
    // -----------------

    // Byte-, char array and strings
    // There are new functions for conversions between
    // ByteArray.decodeToString() and String.encodeToByteArray()
    // CharArray.concatToString() and String.toCharArray()
    val str = "kotlin"
    val array3 = str.toCharArray()
    println(array3.concatToString())

    // Array deque
    // We've also added the ArrayDeque class – an implementation of a
    // double-ended queue. Double-ended queue lets you can add or remove
    // elements both at the beginning and the end of the queue in an
    // amortized constant time. You can use a double-ended queue by default
    // when you need a queue or a stack in your code.

    // The ArrayDeque implementation uses a resizable array underneath: it
    // stores the contents in a circular buffer, an Array, and resizes this
    // Array only when it becomes full.
    val deque = ArrayDeque(listOf(1,2,3))
    deque.addFirst(0)
    deque.addLast(4)

    println("Array data insertion: ${deque}")
    println("Deque array first: ${deque.first()}")
    println("Deque array last: ${deque.last()}")

    deque.removeFirst()
    deque.removeLast()
    println(deque)
}