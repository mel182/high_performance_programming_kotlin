package sequencesExample

fun main() {
    println("Main function called!")

    val listExample = listOf(
            Student("Student 1", 23),
            Student("Student 1", 30),
            Student("Student 2", 33))

    val filtered = listExample.asSequence().filter { it.name == "Student 1" }.map { it }.toList()

    println("Filtered list: $filtered")

}



data class Student(val name:String, val age:Int)