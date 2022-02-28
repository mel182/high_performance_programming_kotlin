package collections

fun main() {
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
    println("combined: ${combined}")

    val combined2 = testListStudent.flatMapIndexed { index: Int, list: List<Student> ->
        if (index in 1..2) list else emptyList()
    }

    println("Combined 2: ${combined2}")
}

data class Student(val name:String, val age:Int)