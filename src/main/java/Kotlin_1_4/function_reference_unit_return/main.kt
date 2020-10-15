package Kotlin_1_4.function_reference_unit_return

fun main() {
    println("Main function called")
    unitFunction { returnsInt() } // this was the only way to do it  before 1.4
    unitFunction(::returnsInt) // starting from 1.4, this also works
}

// In Kotlin 1.4, you can use callable references to functions returning any type in Unit-returning functions. Before Kotlin 1.4, you could only use lambda arguments in this case. Now you can use both lambda arguments and callable references.
fun unitFunction(f: () -> Unit){ }
fun returnsInt(): Int = 42


