package implicit_delegates

// This is an example of a implicit delegates
// Implicit delegation is a language-level feature. With implicit delegation,
// we use a special language construction or keyword to implement delegation
// instead of overriding methods explicitly. There are two types of implicit delegation
// There are two types of implicit delegation:
// * Unanticipated
// * Anticipated
// For unanticipated delegation, a delegate object can be replaced by another dynamically, whereas anticipated
// delegation doesn't let us change a delegate object during the delegating object's life cycle.
// Kotlin supports only anticipated delegation as a language-level feature.
fun main() {
    CalculatorMachine(CalculatorBrain2())
}

class CalculatorMachine(private val delegate: CalculatorBrain) : CalculatorBrain by delegate
{
    init {
        delegate.calculate("10")
    }
}

class CalculatorBrain2 : CalculatorBrain
{
    override fun calculate(value: String): Int {
        val calculatedValue = value + 10
        println("Calculated value: $calculatedValue")
        return 10
    }
}

interface CalculatorBrain
{
    fun calculate(value:String):Int
}