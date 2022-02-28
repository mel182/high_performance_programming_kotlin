package Kotlin_1_5

// This is still in Beta version!
fun main() {
    println("Ubyte: ${(-1).toUByte()}") // 255
    println("Max value: ${UInt.MAX_VALUE + 2u}") // 1
    println("Max value: ${1u..UInt.MAX_VALUE}") // 1..4294967295
}