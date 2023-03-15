package kotlin_1_7.stable_duration

import kotlin.time.Duration.Companion.seconds
// The Duration class for representing duration amounts
// in different time units has been promoted to Stable.
// In 1.6.0, the Duration API has received the following
// changes:
//
// Note: We suggest replacing previously introduced companion
// functions, such as 'Duration.seconds(Int)', and deprecated
// top-level extensions like 'Int.seconds' with new extensions in
// 'Duration.Companion'
//
// Important:
// Such a replacement may cause ambiguity between old top-level
// extensions and new companion extensions. Be sure to use the
// wildcard import of the kotlin.time package
// – import kotlin.time.*
// before doing automated migration
fun main() {
    val duration = 1000
    println("There are ${duration.seconds.inWholeMinutes} minutes in $duration seconds")
    val duration2 = 10000
    println("There are ${duration2.seconds.inWholeHours} hours in $duration2 seconds")
}