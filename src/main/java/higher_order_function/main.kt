package higher_order_function

data class Student(val firstName:String, val lastName:String, val age:Int)

fun main() {

    // An algorithm example
    val studentExample = Student("FirstName","LastName",34)
    var algorithm: (String) -> String = { studentExample.firstName }
    println("Alg 1 test: ${algorithm("test")}")

    // Another example using predicate

    val students = arrayListOf(
            Student("Melchior","Vrolijk",20),
            Student("Elvis","Vrolijk",34),
    )

    val filterAge20 = students.filter(::ageMoreThan20).filter(::firstNameStartsWithE)
    println("Filter age older than 20 and firstname starts with: $filterAge20")

    // Another addition example
    val addition = doAddition()
    print(addition(3,2))

}

fun ageMoreThan20(student:Student):Boolean = student.age > 20
fun firstNameStartsWithE(student:Student):Boolean = student.firstName.toUpperCase().startsWith("E")


fun doAddition():(Int,Int) -> Int {
    return ::addNumbers
}

fun addNumbers(numOne:Int, numTwo:Int) = numOne.plus(numTwo)