package smart_cast_callable_reference

import kotlin.reflect.KFunction

fun main() {
    println("main function called!")
    perform(Cat())

    // Another example on how to use the callable reference
    val test  = Cat()
    test::meow.call()
}

sealed class Animal

class Cat: Animal() {
    fun meow()
    {
        println("meow")
    }
}

class Dog: Animal() {

    fun woof(){
        println("woof")
    }
}

fun perform(animal:Animal)
{
    val kFunction: KFunction<*> = when(animal){
        is Cat -> animal::meow
        is Dog -> animal::woof
    }
    kFunction.call()
}