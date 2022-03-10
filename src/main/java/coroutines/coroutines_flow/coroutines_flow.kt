package coroutines.coroutines_flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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
//        executeNumberAsFlowExample2()
//        countdownTimerExample()
//        countExample()
//        reducerExample()
//        foldExample()
        flattenFlowExample()
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

private suspend fun countdownTimerExample() {

    val countdownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }

    countdownFlow.collect{ time ->
        println("collected int: ${time}")
    }

    println("|-------- collect latest example -------|")
    // collect latest will collect ONLY the latest in case there is a delay
    countdownFlow.collectLatest{ time ->
        delay(1500) // simulate delay, in this case it will delay it
                             // for 1.5 sec while every 1 sec value is coming
                             // in.
        println("collected latest int: ${time}")
    }
    println("|-------- collect latest example -------|")
    println("collected ended")
}

private suspend fun countExample() {

    val countdownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }

    val count = countdownFlow.onEach {
        println("Item found: $it")
        it
    }.count { it > 5 }
    println("Count result: $count")
}

private suspend fun reducerExample() {

    val countdownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            currentValue--
            emit(currentValue)
        }
    }

    // Accumulates value starting with the first element and
    // applying operation to current accumulator (10 in this case) value
    val reducer = countdownFlow.reduce { accumulator, value ->
        println("Item found, accumulator: $accumulator and value: $value")
        accumulator
    }
    println("Reducer result: $reducer")
}

private suspend fun foldExample() {

    val countdownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            currentValue--
            emit(currentValue)
        }
    }

    // Accumulates value starting with initial value (15 in this case) and applying operation
    // current accumulator value and each element
    val reducer = countdownFlow.fold(15) { accumulator, value ->
        println("Item found, accumulator: $accumulator and value: $value")
        accumulator
    }
    println("Reducer result: $reducer")
}

private suspend fun flattenFlowExample() {

    val flow1 = flow<Int> {
        emit(1)
        delay(500)
        emit(2)
    }

    println("|--------- flat map concat ---------|")
    // This can be used when you want to retrieve data from your local database and
    // remote server and combine them into one flow
    flow1.flatMapConcat { value ->

        flow {
            emit(value + 1)
            delay(500)
            emit(value + 2)
        }
    }.collect{ value ->
        println("map concat value: $value")
    }
    println("|--------- flat map concat ---------|")
}

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