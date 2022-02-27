package kotlin_1_6.bit_rotation_operations_on_integer

// In Kotlin 1.6.0, the rotateLeft() and rotateRight()
// functions for bit manipulations became Stable.
// The functions rotate the binary representation
// of the number left or right by a specified number
// of bits.
fun main() {
    val number:Short = 0b10001
    println(number.rotateRight(2).toString(radix = 2))
    println(number.rotateLeft(2).toString(radix = 2))
}