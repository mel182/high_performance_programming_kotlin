package inmutable

import java.util.*

fun main() {

    var counter = 0

    val inc = {
        counter++
    }

    inc()

    println("Main called: $counter")

}

interface ValueHolder<T>{
    val value:T
}

class IntHolder: ValueHolder<Int>
{
    override val value: Int
        get() = Random().nextInt()
}