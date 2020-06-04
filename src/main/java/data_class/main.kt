package data_class

fun main() {

    var person = Person("Melchior",33)

    person?.let {
        it.name = "Melchior182"
        it
    }

    println("Person new: "+person)

    val person2 = person.copy(name = "Melchior")

    println("Person2: "+person2)


    val equals = person2 == person2

    println("Equals: "+equals)

    val testString = "Test String"

    println("Test string: ${testString.intern() === testString.intern()}")


}

data class Person(var name: String, var age:Int)