package stableSuspendConversions

import kotlinx.coroutines.runBlocking

// Kotlin 1.6.0 introduces Stable conversions from regular to suspending functional types.
// Starting from 1.4.0, the feature supported functional literals and callable references.
// With 1.6.0, it works with any form of expression. As a call argument, you can now pass
// any expression of a suitable regular functional type where suspending is expected.
// The compiler will perform an implicit conversion automatically.
fun main() {

   test { }
}

fun getSuspending(suspending: suspend() -> Unit) {

    runBlocking {
        suspending.invoke()
    }
}
fun suspending() {
    println("suspending called!")
}
fun test(regular: () -> Unit) {
    getSuspending {  } // ok
    getSuspending(::suspending) // ok
    getSuspending(regular) // ok
}


