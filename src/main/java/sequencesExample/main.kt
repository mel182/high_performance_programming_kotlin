package sequencesExample

fun main() {
    println("Main function called!")

    val listExample = ArrayList<Student>()
    listExample.add(Student("Student 1", 23))
    listExample.add(Student("Student 1", 30))
    listExample.add(Student("Student 2", 33))
    listExample.add(Student("Student 4", 40))

    val updatedList = arrayListOf<Student>(
        Student("Student 2", 100),
        Student("Student 4", 150)
    )

    val filtered = listExample.asSequence().filter { it.name == "Student 1" }.map { it }.toList()
    val filtered2 = listExample.asSequence().filter { it.name == "Student 2" }.map { it }.toList()
//    val filtered2 = listExample.asSequence().filter { it.name == "Student 2" }.first() // Get first item from list
    // Note: It will throw a runtime exception if not found


    for (studentFound in updatedList)
    {
        listExample.asSequence().any{ student -> student.name == studentFound.name }.also {

            if (it)
            {
                val item = listExample.asSequence().filter { it.name == studentFound.name }.first()
                item.age = studentFound.age
            } else {
                listExample.add(studentFound)
            }
        }
    }

    for (studentFound in listExample) {
        println("Student found: ${studentFound}")
    }

    // Check if list contain a list of 'Student 2'
    listExample.any{ it.name == "Student 2"}.also { listContainsItem ->

        if (listContainsItem)
        {
            // It contains item
        } else {
            // It does not contain item
        }
    }

    println("Filtered : $filtered")
    println("Filtered2 : $filtered2")
}



data class Student(val name:String, var age:Int)