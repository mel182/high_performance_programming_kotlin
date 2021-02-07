package collections

// An array stores multiple items of the same type in one fixed-size sequence.
// It's easy to access and modify any element of an array by referencing its index.
// Arrays are an efficient way of reducing the number of variables and grouping them by logic and type.
// In Java and Kotlin, a multidimensional array is an array of arrays; it can be used to represent matrices or some figures in space.
// You can create arrays with different depth levels, but it's hard to manage arrays with more than three levels of depth.
//
// An array supports operations such as:
// - get(index: Int): T
// - set(index: Int, value: T): Unit
//
fun main() {

    val array = arrayOf(1, 2, 3, 4)

    array[0] = 5 // set(index: Int, value: T): Unit
    val valueAtIndex0 = array[0] //get(index: Int): T
    println("Value at index 0: ${valueAtIndex0}")

    array.forEach {
        println("Item found: ${it}")
    }
}