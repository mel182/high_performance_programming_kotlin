package inner_classes

class Main {

    // The correct way to implement an inner class for such use-case
    inner class Inner {

        fun printValue()
        {
            println(value)
        }
    }

    @JvmField var value = "Value"
    // --------------------------------------------------------------

}