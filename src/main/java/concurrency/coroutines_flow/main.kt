package concurrency.coroutines_flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {

        println("Receiving numbers")
//        sendNumbers().collect {
//            println("Received number: $it")
//        }
//        sendNumbersAsFlow().collect {
//            println("Received number builder: $it")
//        }

        sendNumbersAsFlow().map {
            delay(300)
            it
        }
        .filter { it <= 10 }
        .onEach {

            println("Value $it")
            check( it != 7)
        }
        .catch { e -> println("Caught exception $e") }
        .onCompletion { println("Flow completed") }
        .transform {
          emit("Emitting string $it")
        }.collect {
            println("Received number builder: $it")
        }

//        flowOfExample().collect {
//            println("Received number builder: $it")
//        }
        println("Finished receiving numbers")

        println("|---------- Filter Example -----------|")

        sendNumbersAsFlow().map {
            it + 2
        }.filter {
            it == 7
        }.collect {
            println("Filter result: $it")
        }

        println("|---------- Filter Example -----------|")

    }
}

// This emit number with delay
fun sendNumbers(): Flow<Int> = flow {

    val primeList = listOf(2,3,5,7,11,13,17,19,23,29)
    primeList.forEach {
        delay(it * 100L)
        emit(it)
    }
}

// This is an example using the 'as flow' builder
fun sendNumbersAsFlow() = listOf(2,3,5,7,11,13,17,19,23,29).asFlow()

// This is an example using the 'flow of' builder
fun flowOfExample() = flowOf("One","Two","Three")


