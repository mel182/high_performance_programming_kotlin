package coroutines.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//region Main function
fun main() {
//    exampleBlocking2()
//    exampleBlockingDispatcher()
//    exampleLaunchGlobal()
//    exampleLaunchGlobalWaiting()
//    exampleLaunchCoroutineScope()
//    exampleLaunchCoroutineWithCustomDispatcher()
    exampleAsyncAwait()
//    exampleWithContext()
}
//endregion

//region Example blocking 1
private fun exampleBlocking1(){
    println("One")
    runBlocking {
        printlnDelayed("Two")
    }
    println("Three")
}
//endregion

//region Print delay with thread
suspend fun printlnDelayed(message:String)
{
    // Complex calculation
//    Thread.sleep(1000) //With normal threads
    delay(1000) // with concurrency.runnable.coroutines
    println(message)
}
//endregion

//region Calculate things
suspend fun calculateThings(number:Int) : Int
{
    delay(1000) // Coroutines delay
    return number * 10
}
//endregion

//region Example blocking 2
fun exampleBlocking2() = runBlocking{
    println("One")
    printlnDelayed("Two")
    println("Third")
}
//endregion

//region Example blocking dispatcher
// Running on another thread but still blocking the main.kt thread
fun exampleBlockingDispatcher(){
    runBlocking(Dispatchers.Default) {
        println("One - from thread ${Thread.currentThread().name}")
        printlnDelayed("Two - from thread ${Thread.currentThread().name}")
    }
    // Outside of runBlocking to show that it's running in the blocked main.kt thread
    println("three - from thread ${Thread.currentThread().name}")
    // It still runs only after the runBlocking is fully executed.
}
//endregion

//region Example launch global
fun exampleLaunchGlobal(){
    runBlocking {
        println("one - from thread ${Thread.currentThread().name}")

        GlobalScope.launch {
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }

        println("three - from thread ${Thread.currentThread().name}")
        delay(3000)
    }
}
//endregion

//region Example launch global waiting
fun exampleLaunchGlobalWaiting(){
    runBlocking {
        println("one - from thread ${Thread.currentThread().name}")

        val job = GlobalScope.launch {
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }

        println("three - from thread ${Thread.currentThread().name}")
        println("Job: ${job.status()}")

        job.join()
    }
}

fun Job.status(): String = when {
    isCancelled -> "cancelled"
    isActive -> "Active"
    isCompleted -> "Complete"
    else -> "Nothing"
}

//endregion

//region Example launch coroutine scope
fun exampleLaunchCoroutineScope(){
    runBlocking {
        println("one - from thread ${Thread.currentThread().name}")

        // Default dispatcher
//        launch(Dispatchers.Default) {
//            printlnDelayed("two - from thread ${Thread.currentThread().name}")
//        }

        // IO dispatcher (suitable for network IO, diskIO since it rapidly creates more threads if needed)
        launch(Dispatchers.IO) {
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }

        // This dispatcher is intended for android to switch back to main.kt thread
//        launch(Dispatchers.Main) {
//            printlnDelayed("two - from thread ${Thread.currentThread().name}")
//        }

        println("three - from thread ${Thread.currentThread().name}")
    }
}
//endregion

//region Example launch coroutine with custom dispatcher
fun exampleLaunchCoroutineWithCustomDispatcher(){
    runBlocking {
        println("one - from thread ${Thread.currentThread().name}")

        val customDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

        // IO dispatcher (suitable for network IO, diskIO since it rapidly creates more threads if needed)
        launch(customDispatcher) {
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }

        println("three - from thread ${Thread.currentThread().name}")
        (customDispatcher.executor as ExecutorService).shutdown()
    }
}
//endregion

//region Example launch coroutine async await
fun exampleAsyncAwait(){

        println("Has entered IO coroutine scope!")

        runBlocking {

            // Use this if you want to run things concurrently
            val startTime = System.currentTimeMillis()

            //region This is the best practice since it takes around 1027 millisecond to executes
//            val deferred1 = async { calculateThings(10) }
//            val deferred2 = async { calculateThings(20) }
//            val deferred3 = async { calculateThings(30) }
//
//            val sum = deferred1.await() + deferred2.await() + deferred3.await()
            //endregion

            //region This is a bad practice since using await makes the execution takes 3000 millisecond
        val deferred1 = async { calculateThings(10) }.await()
        val deferred2 = async { calculateThings(20) }.await()
        val deferred3 = async { calculateThings(30) }.await()
//
        val sum = deferred1 + deferred2 + deferred3
            //endregion

            val divided = async { sum/3 }.await()

            println("async/await result = $divided")

            val endTime = System.currentTimeMillis()

            println("Time taken: ${endTime - startTime}")
        }
}
//endregion

//region Example launch coroutine with context
fun exampleWithContext(){
    runBlocking {

        // Use this if you don't want to run things concurrently
        val startTime = System.currentTimeMillis()

        val result1 = withContext(Dispatchers.IO) { calculateThings(10) }
        val result2 = withContext(Dispatchers.IO) { calculateThings(20) }
        val result3 = withContext(Dispatchers.IO) { calculateThings(30) }

        val sum = result1 + result2 + result3

        println("async/await result = $sum")

        val endTime = System.currentTimeMillis()

        println("Time taken: ${endTime - startTime}")
    }
}
//endregion
