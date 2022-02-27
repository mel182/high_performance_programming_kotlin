package kotlin_1_6.stableSuspendingFunctionAsSupertype

import kotlinx.coroutines.*

// Implementation of suspending functional types has become Stable in Kotlin 1.6.0.
// A preview was available in 1.5.30.

// The feature can be useful when designing APIs that use Kotlin coroutines and accept
// suspending functional types. You can now streamline your code by enclosing the desired
// behavior in a separate class that implements a suspending functional type.
//
// There are currently two limitations coming from implementation details:
// * You can't mix ordinary functional types and suspending ones in the list of supertypes.
// * You can't use multiple suspending functional supertypes.
class MyClickAction : suspend () -> Unit {

    init {
        println("class init called!")
    }

    override suspend fun invoke() {
        println("invoke")
    }
}

fun launchOnClick(action: suspend () -> Unit) {
    println("invoking function...")

    runBlocking {
        println("inside IO coroutine")
        action.invoke()
    }
}

fun main() {

    // usage
    launchOnClick(MyClickAction())
}