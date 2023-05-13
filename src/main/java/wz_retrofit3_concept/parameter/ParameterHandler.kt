package wz_retrofit3_concept.parameter

import org.jetbrains.kotlinx.serialization.compiler.backend.jvm.ARRAY
import wz_retrofit3_concept.request.RequestBuilder
import java.io.IOException
import java.util.Arrays


abstract class ParameterHandler<T> {

    @Throws(IOException::class)
    abstract fun apply(builder: RequestBuilder, values: T?)

    fun iterable(): ParameterHandler<Iterable<T>> {

        return object : ParameterHandler<Iterable<T>>() {
            override fun apply(builder: RequestBuilder, values: Iterable<T>?) {

                if (values == null) // Skip null values.
                    return

                for (valueFound in values) {
                    this@ParameterHandler.apply(builder, valueFound)
                }
            }
        }
    }

    fun array(): ParameterHandler<Any> {

        return object : ParameterHandler<Any>() {

            override fun apply(builder: RequestBuilder, values: Any?) {

                if (values == null) // Skip null values.
                    return

                run {
                    var i = 0
                    val size: Int = java.lang.reflect.Array.getLength(values)
                    while (i < size) {
                        //noinspection unchecked
                        this@ParameterHandler.apply(builder, java.lang.reflect.Array.get(values, i) as T)
                        i++
                    }
                }
            }
        }
    }

}