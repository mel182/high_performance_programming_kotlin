package wz_retrofit3_concept.interfaces

import java.io.IOException

interface Call<T>: Cloneable {

    @Throws(IOException::class)
    fun execute()

    fun enqueue()

}