package function_composition

fun main() {

    val students = listOf(
            Student("Eric",25),
            Student("Melchior",33)
    )

    val studentsFiltered = students.filter(::ageMoreThan24)

    println("Student filtered: $studentsFiltered")

    val studentsFiltered2 = students.filter(::ageMoreThan24 and ::firstNameStartWithE)

    println("Student filtered 2: $studentsFiltered2")
}

// Function composition
fun ageMoreThan24(student:Student) = student.age > 24
fun firstNameStartWithE(student:Student) = student.name.startsWith("e",true)

// Function that implement function composition
inline infix fun <P> ((P) -> Boolean).and(crossinline predicate: (P) -> Boolean): (P) -> Boolean {
    return { p:P -> this(p) && predicate(p) }
}

data class Student(val name:String, val age:Int)
