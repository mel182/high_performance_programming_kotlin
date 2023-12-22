@file:OptIn(ExperimentalTime::class)

package time_mark

import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

// Kotlin 1.7.0 improves the performance of time measurement functionality
// by changing the time marks returned by TimeSource.Monotonic into inline
// value classes.
// This means that calling functions like markNow(), elapsedNow(), measureTime(),
// and measureTimedValue() doesn't allocate wrapper classes for their TimeMark instances.
// Especially when measuring a piece of code that is part of a hot path, this can help
// minimize the performance impact of the measurement:

@OptIn(ExperimentalTime::class)
fun main() {

    val mark = TimeSource.Monotonic.markNow() // Returned `TimeMark` is inline class
    val elapsedDuration = mark.elapsedNow()
    println("elapsed duration: $elapsedDuration")
}