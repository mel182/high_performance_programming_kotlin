package Kotlin_1_4.delegate_properties

import kotlin.properties.Delegates

fun main() {

    var prop: String? by Delegates.observable(null){ p, old, new ->
        println("$old -> $new")
    }

    prop = "abc"
    prop = "xyz"

    println("prop: ${prop}")
}