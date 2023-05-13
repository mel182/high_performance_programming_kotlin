package property_delegation

import property_delegation.normal_class.CustomDelegate
import property_delegation.normal_class.DoubleDelegate
import property_delegation.singleton.SingletonDelegate
import java.math.BigInteger

fun main() {
    var value by CustomDelegate<String>()
    value = "functionAsParameter"
    println("value: $value")

    // Singleton example
    val property by SingletonDelegate
    val anotherProperty by SingletonDelegate

    println("Property: $property")
    println("Another property: $anotherProperty")

    // -------- Double delegate -------- \\
    var doubleValue by DoubleDelegate()
    doubleValue = 1.0
    println("Double value: $doubleValue")
    // -------- Double delegate -------- \\

    // The Lazy delegate supports lazy evaluation. It's a common strategy
    // that delays invoking an expression until its value is needed and
    // caches the value to avoid repeated evaluations.
    // Using this strategy can increase performance and avoid error conditions
    // during computation.
    val lazyValue by lazy(LazyThreadSafetyMode.PUBLICATION) {
        println("Computation")
        BigInteger.valueOf(2).modPow(BigInteger.valueOf(7), BigInteger.valueOf(20))
    }
    println("Lazy value: ${lazyValue}")

    // The Lazy delegate supports three modes:
    // Here is a short explanation of the different modes:
    // - SYNCHRONIZED mode: uses locks to ensure that only a single thread can evaluate a value.
    // - PUBLICATION mode: allows several threads to initialize a value, but only the first returned result will be used as the value.
    // - NONE mode: allows several threads to initialize a value, but its behavior is undefined.

    val testArray = arrayOf(1,2,3,4,5,6,7,8,9,10)

    println("size: ${testArray.size}")
    for (i in testArray.indices) {
        println("index: $i -> value: ${testArray[i]}")
    }

}