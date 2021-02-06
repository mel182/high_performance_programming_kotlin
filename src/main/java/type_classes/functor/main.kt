package type_classes.functor

import arrow.core.Option

// A Functor is a typeclass that can be mapped. You can consider it an interface that provides
// the map method to map one value to another. From an object-oriented programming point of view,
// it's a class that implements the Mappable interface. But a Functor is something that can be
// applied to any type, which is why, in Kotlin, the Functor is implemented as something like
// a wrapper class.
// Use the 'implementation "io.arrow-kt:arrow-core:0.11.0"' dependency in order to use it.
fun main() {
    println("Main function called!")
    // An example on how to use the Functor type class

    val bear = Bear(5)
    var teddy:Teddy? = null

    Option<Bear>(bear).map {
        teddy = Teddy(it.age)
    }

    println("Teddy bear: ${teddy}")

}

data class Bear(val age:Int)
data class Teddy(val age:Int)