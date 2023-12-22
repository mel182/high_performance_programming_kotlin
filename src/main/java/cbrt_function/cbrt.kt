@file:OptIn(ExperimentalStdlibApi::class)

package cbrt_function

import kotlin.math.cbrt

//The cbrt() function, which allows you to compute the real cube
// root of a double or float, is now Stable.

fun main() {

    val num = 27
    val negativeNum = -num

    println("The cube root of ${num.toDouble()} is ${cbrt(num.toDouble())}") // 3.0
    println("The cube root of ${negativeNum.toDouble()} is: ${cbrt(negativeNum.toDouble())}") // -3.0

}