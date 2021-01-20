package delegates

import kotlin.properties.Delegates

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