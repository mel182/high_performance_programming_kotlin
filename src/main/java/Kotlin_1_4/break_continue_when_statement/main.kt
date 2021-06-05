package Kotlin_1_4.break_continue_when_statement

//In Kotlin 1.4, you can use break and continue without
//labels inside when expressions included in loops.
// They behave as expected by terminating the nearest
// enclosing loop or proceeding to its next step.
fun main() {
    println("Main function called!")

    val testList = listOf(
            2,5,18,17,19
    )
    test(testList)
}

fun test(list: List<Int>)
{
    for (index in list)
    {
        when(index)
        {
            2 -> {
                println("index: ${index}")
                continue
            }

            17 -> {
                println("index: ${index}")
                break
            }

            else -> println(index)
        }
    }
}