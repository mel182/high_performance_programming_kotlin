package wz_retrofit3_concept.call

import wz_retrofit3_concept.Retrofit3
import wz_retrofit3_concept.interfaces.CallAdapter
import java.lang.reflect.Type

class CompletableFutureCallAdapterFactory: CallAdapter.Factory() {

    companion object {
        val INSTANCE = CompletableFutureCallAdapterFactory()
    }

    override fun get(returnType: Type?, annotations: Array<Annotation>?, retrofit: Retrofit3?): CallAdapter<*, *>? {
        TODO("Not yet implemented")
    }


}