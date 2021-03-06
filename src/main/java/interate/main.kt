package interate

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() {

    val list:ArrayList<String> = ArrayList()
    val studentList:ArrayList<Student> = ArrayList()

    for (index in 0..100)
    {
        list.add("Value $index")
    }

    for (index in 0..100)
    {
        studentList.add(Student("Student $index",index))
    }

    runBlocking {

        val forEachExample = async { iterateUsingForEach(list) }.await()
        val forLoopExample = async { iterateUsingForLoop(list) }.await() // fast
        val customForLoopExample = async { iterateUsingCustomForLoop(list) }.await() // faster

        val forEachStudentExample = async { iterateUsingForEachStudent(studentList) }.await()
        val forLoopStudentExample = async { iterateUsingForLoopStudent(studentList) }.await() // fast
        val customForLoopStudentExample = async { iterateUsingCustomForLoopStudent(studentList) }.await() // faster

        println("\nFor each duration: $forEachExample\nFor loop example: $forLoopExample\nCustom for loop example: $customForLoopExample")
        println("\nFor each student duration: $forEachStudentExample\nFor loop student example: $forLoopStudentExample\nCustom for loop student example: $customForLoopStudentExample")
    }
}

suspend fun iterateUsingForEach(list:List<String>) : Long
{
    val startTime = System.currentTimeMillis()
    list.forEach {
        println("Item found: $it")
    }
    val endTime = System.currentTimeMillis()

    return endTime - startTime
}

suspend fun iterateUsingForLoop(list:List<String>) : Long
{
    val forLoopStartTime = System.currentTimeMillis()
    for (item in list)
    {
        println("for loop Item found: $item")
    }
    val forLoopEndTime = System.currentTimeMillis()

    return forLoopEndTime - forLoopStartTime
}

suspend fun iterateUsingCustomForLoop(list:List<String>) : Long
{
    val customForLoopStartTime = System.currentTimeMillis()
    list.customForeach{
        println("custom for loop Item found: $it")
    }
    val customForLoopEndTime = System.currentTimeMillis()

    return customForLoopEndTime - customForLoopStartTime
}

suspend fun iterateUsingForEachStudent(list:List<Student>) : Long
{
    val startTime = System.currentTimeMillis()
    list.forEach {
        println("Item found: $it")
    }
    val endTime = System.currentTimeMillis()

    return endTime - startTime
}

suspend fun iterateUsingForLoopStudent(list:List<Student>) : Long
{
    val forLoopStartTime = System.currentTimeMillis()
    for (item in list)
    {
        println("for loop Item found: $item")
    }
    val forLoopEndTime = System.currentTimeMillis()

    return forLoopEndTime - forLoopStartTime
}

suspend fun iterateUsingCustomForLoopStudent(list:List<Student>) : Long
{
    val customForLoopStartTime = System.currentTimeMillis()
    list.customForeach{
        println("custom for loop Item found: $it")
    }
    val customForLoopEndTime = System.currentTimeMillis()

    return customForLoopEndTime - customForLoopStartTime
}


// the foreach function with while is the fastest and you should
// consider creating your own foreach method.
inline fun <reified T> List<T>.customForeach(crossinline invoke: (T) -> Unit): Unit {
    val size = size
    var i = 0
    while (i < size)
    {
        invoke(get(i))
        i++
    }
}

data class Student(val name:String, val age:Int)