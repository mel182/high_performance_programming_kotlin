@file:OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)

package kotlin_1_6.coroutines

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext

// In kotlinx.coroutines 1.6.0, the new dispatcher views API is introduced as an option to
// limit concurrency without creating additional threads and allocating extra resources.
// To start using dispatcher views, just call 'limitedParallelism()' instead of 'newFixedThreadPoolContext()'.
//
// The new approach addresses the limitations of using custom thread pools:
//
// - A dispatcher view is only a wrapper to the original dispatcher. Using the original dispatcher’s resources,
//   it limits the number of coroutines that can be executed simultaneously and doesn’t create new threads.
// - The 'withContext()' invocation with 'Dispatchers.Default', 'Dispatchers.IO', or their views attempts not to
//   switch threads when possible.
// - A view doesn't need to be closed
// - To create seperate executors, you can take multiple views of the same dispatcher and they will share threads
//   and resources. There is not limit on the total parallelism value, but the effective parallelism of all views cannot
//   exceed the actual parallelism of the original dispatcher. This means that you can control parallelism of both entire
//   application and each view separately.

val backgroundDispatcher = newFixedThreadPoolContext(5,"App background")
// At most 2 threads will process images
val imageProcessingDispatcher = backgroundDispatcher.limitedParallelism(2)
// At most 1 thread will do IO
val fileWriterDispatcher = backgroundDispatcher.limitedParallelism(1)
// At most 3 threads will handle database requests
val adDispatcher = backgroundDispatcher.limitedParallelism(3)