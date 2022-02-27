package Kotlin_1_4.delegate_properties

import kotlin.properties.Delegates

fun main() {

    var prop: String? by Delegates.observable(null){ p, old, new ->
        println("p -> $p")
        println("$old -> $new")
    }

    prop = "abc"
    prop = "xyz"
    prop = "abc"
    prop = "abc"

    println("prop: ${prop}")
}