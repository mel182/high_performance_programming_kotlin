package delegates

import kotlin.properties.Delegates

fun main() {

    CalculatorMachine(CalculatorBrain2())

}


class CalculatorMachine(private val delegate: CalculatorBrain) : CalculatorBrain by delegate

class CalculatorBrain2 : CalculatorBrain
{
    override fun calculate(value: String): Int {
        TODO("Not yet implemented")
    }

}


interface CalculatorBrain
{
    fun calculate(value:String):Int
}