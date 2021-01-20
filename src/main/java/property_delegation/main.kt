package property_delegation

import property_delegation.normal_class.CustomDelegate
import property_delegation.normal_class.DoubleDelegate
import property_delegation.singleton.SingletonDelegate

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
}