package jvmFieldExample

fun main() {
    println("main function called!")
}


class Point(@JvmField var x:Int, @JvmField var y:Int)