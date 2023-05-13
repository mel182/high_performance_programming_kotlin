package wz_retrofit3_concept.call

import wz_retrofit3_concept.Retrofit3
import wz_retrofit3_concept.interfaces.CallAdapter
import java.lang.reflect.Type
import java.util.concurrent.Executor

class DefaultCallAdapterFactory(private val callbackExecutor:Executor?): CallAdapter.Factory() {



    override fun get(returnType: Type?, annotations: Array<Annotation>?, retrofit: Retrofit3?): CallAdapter<*, *>? {
        TODO("Not yet implemented")
    }




}