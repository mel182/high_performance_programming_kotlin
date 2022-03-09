package coroutines.coroutines_flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * Coroutine flows
 * - Flow is Kotlin language feature that serves as a reactive programming framework
 * - It's all about being notified about changes in your code and sending them through a
 *   pipeline that potentially modifies them.
 * - A flow is a coroutine that can emit multiple values over a period of time
 */
fun main() {

    runBlocking {

//        executeEmitNumberExample()
//        executeFlowBuilderExample()
//        executeNumberAsFlowExample()
        executeNumberAsFlowExample2()
    }
}

//region Execute flow builder example
private suspend fun executeFlowBuilderExample()
{
    println("\n|----------- execute flow builder example ----------|\n")
    flowOfExample().collect {
        println("Received number builder: $it")
    }
    println("\n|----------- execute flow builder example ----------|\n")
}
//endregion

//region Execute emit number example
private suspend fun executeEmitNumberExample() {
    println("\n|----------- execute emit number example ----------|\n")
    sendNumbers().collect {
        println("Received number: $it")
    }
    println("\n|----------- execute emit number example ----------|\n")
}
//endregion

//region Execute number as flow example
private suspend fun executeNumberAsFlowExample() {
    println("\n|----------- execute number as flow example ----------|\n")
    sendNumbersAsFlow().map {
        delay(300)
        it
    }
    .filter { it <= 10 }
    .onEach {

        println("Value $it")
        check(it != 7)
    }
    .catch { e -> println("Caught exception $e") }
    .onCompletion { println("Flow completed") }
    .transform {
        emit("Emitting string $it")
    }.collect {
        println("Received number builder: $it")
    }
    println("\n|----------- execute number as flow example ----------|\n")
}
//endregion

//region Execute number as flow example 2
private suspend fun executeNumberAsFlowExample2() {
    println("\n|----------- execute number as flow example 2 ----------|\n")
    sendNumbersAsFlow().map {
        it + 2
    }.filter {
        it == 7
    }.collect {
        println("Filter result: $it")
    }
    println("\n|----------- execute number as flow example 2 ----------|\n")
}
//endregion

// This emit number with delay
fun sendNumbers(): Flow<Int> = flow {

    val primeList = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
    primeList.forEach {
        delay(it * 100L)
        emit(it)
    }
}

// This is an example using the 'as flow' builder
fun sendNumbersAsFlow() = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29).asFlow()

// This is an example using the 'flow of' builder
fun flowOfExample() = flowOf("One", "Two", "Three")