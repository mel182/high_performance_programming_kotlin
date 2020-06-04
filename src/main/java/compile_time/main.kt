package compile_time

import java.awt.Point


const val compileTime:Int = 5

fun compileTimeFunction(compileTime:Int){
    val result = compileTime + compileTime
    println("Result: $result")
}

//val point = Point()
//
//const val compileTime:Int = 5
//
//fun compileTimeFunction(compileTime:Int) = compileTime + compileTime
