package Kotlin_1_4.collections

import java.lang.StringBuilder

fun main() {

    // setOfNotNull(), which makes a set consisting
    // of all the non-null items among the provided
    // arguments.
    val set = setOfNotNull(null,1,2,0,null)
    println("Set result: $set")

    // shuffled sequence
    val numbers = (0 until 50).asSequence()
    val result = numbers.map { it * 2 }.shuffled().take(5)
    println("Shuffle result: ${result.toList()}")

    // onEachIndexed
    listOf("b","c","d").onEachIndexed {
        index, item -> println("${index}:${item}")
    }

    // flatmap
    val list = listOf("hello","kot","lin","world")
    val kotlin = list.flatMapIndexed { index: Int, s: String ->
        if (index in 1..2) s.toList() else emptyList()
    }
    println(kotlin)

    val studentList1 = listOf(
            Student("Student1",24),
            Student("Student2",20),
            Student("Student3",21),
            Student("Student4",22),
            Student("Student5",23),
            Student("Student6",19),
            Student("Student7",18),
            Student("Student8",16),
    )

    val studentList2 = listOf(
            Student("Student1",24),
            Student("Student2",20),
            Student("Student3",21),
            Student("Student4",22),
            Student("Student5",23),
            Student("Student6",19),
            Student("Student7",18),
            Student("Student8",16),
    )

    val testListStudent = listOf(studentList1,studentList2)
    val combined = testListStudent.flatMap { it }
    println(combined)

    val combined2 = testListStudent.flatMapIndexed { index: Int, list: List<Student> ->
        if (index in 1..2) list else emptyList()
    }

    println("Combined 2: ${combined2}")

    //sumOf()
    val total = studentList1.sumOf { it.age * 2 }
    val count = studentList1.sumOf { it.age }

    println("total: ${total}")
    println("count: ${count}")

    // max of
    val maxOf = studentList1.maxOf { it.age }

    // min of
    val minOf = studentList1.minOf { it.age }

    println("max of ${maxOf}")
    println("min of ${minOf}")

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
    val array = str.toCharArray()
    println(array.concatToString())

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

data class Student(val name:String, val age:Int)




