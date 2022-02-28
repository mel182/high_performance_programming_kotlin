package coroutines.global_scope

import coroutines.coroutines.printlnDelayed
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class) // To ignore global scope warning
fun main() {
    // Global scope is marked as delicate API

    // Tip: A practical use-case were global scope can be used, perform a
    // background task during the entire lifecycle of the app
    @OptIn(DelicateCoroutinesApi::class)
    GlobalScope.launch {
        printlnDelayed("thread name: ${Thread.currentThread().name}")
    }

}