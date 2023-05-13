package wz_retrofit3_concept.call

import wz_retrofit3_concept.interfaces.Call
import java.util.concurrent.Executor

class ExecutorCallbackCall<T>(val callbackExecutor: Executor?, val delegate: Call<T>): Call<T> {
    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun enqueue() {
        TODO("Not yet implemented")
    }


}