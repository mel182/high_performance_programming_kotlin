@file:OptIn(ExperimentalCoroutinesApi::class)

package kotlin_1_6.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

// 'Dispatchers.IO' elasticity for limited parallelism
//
// Imagine a case where your application uses multiple views as separate executors
// and needs each one to guarantee a specified level of parallelism during peak loads.
// You don’t need to create a parent dispatcher with the desired total parallelism value.
// Instead, you can use views of Dispatchers.IO which can create and shutdown additional
// threads on-demand, saving resources in a steady state.
//
// The implementation of limitedParallelism() for Dispatchers.IO is elastic.
// This means that Dispatchers.IO itself still has a limit of 64 threads, but each of its views
// will have the effective parallelism of the requested value.

// 80 threads for PostgreSQL connection
val myPostgresqlDbDispatcher = Dispatchers.IO.limitedParallelism(80)
// 40 threads for MongoDB connection
val myMongoDbDispatcher = Dispatchers.IO.limitedParallelism(40)

// In the example:
// - During peak loads, the system may have up to 64 + 80 + 40 threads dedicated to blocking tasks.
// - In a steady state, there will be only a small number of threads shared among Dispatchers.IO,
//   'myPostgresqlDbDispatcher', and 'myMongoDbDispatcher'.
//
// Under the hood, it works with an additional dispatcher backed by an unlimited pool of threads.
// Both Dispatchers.IO and its views are actually views of that dispatcher and share threads and resources.