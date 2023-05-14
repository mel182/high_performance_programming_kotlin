package wz_retrofit3_concept.extensions

import kotlin.NoSuchElementException

@Throws(NoSuchElementException::class)
fun Array<Any>.indexOf(toFind:Any): Int {

    for (index in indices)
    {
        if (toFind == this[index])
            return index
    }

    throw NoSuchElementException()
}