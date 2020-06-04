package compile_time

// This is the best way to use inner for the best performance

class Main {

    private val value = "value"

    val test = Inner().printValue()

    inner class Inner{
        fun printValue()
        {

        }
    }

}