package collections

fun printAll(strings: Collection<String>) {
    for(s in strings) print("$s ")
    println()
}
fun main() {

    // MutableCollection<T> is a Collection with write operations,
    // such as add and remove.
    val stringList = listOf("one", "two", "one") // one two one
    printAll(stringList)

    val stringSet = setOf("one", "two", "three") // one two three
    printAll(stringSet)

    println("")
    println("|-------------------- List example -------------------|")
    // List<T> stores elements in a specified order and provides indexed
    // access to them. Indices start from zero – the index of the first
    // element – and go to lastIndex which is the (list.size - 1)
    val numbers = listOf("one", "two", "three", "four")
    println("Number of elements: ${numbers.size}")
    println("Third element: ${numbers[2]}")
    println("Fourth element: ${numbers[3]}")
    println("Index of element \"two\" ${numbers.indexOf("two")}")
    println("|-------------------- List example -------------------|")

    // List elements (including nulls) can duplicate: a list can contain any
    // number of equal objects or occurrences of a single object. Two lists
    // are considered equal if they have the same sizes and structurally equal
    // elements at the same positions.
    println("")
    println("|-------------------- List elements (including nulls) -------------------|")
    data class Person(var name: String, var age: Int)
    val bob = Person("Bob", 31)
    val people = listOf(Person("Adam", 20), bob, bob)
    val people2 = listOf(Person("Adam", 20), Person("Bob", 31), bob)
    println(people == people2)
    bob.age = 32
    println(people == people2)
    println("|-------------------- List elements (including nulls) -------------------|")

}