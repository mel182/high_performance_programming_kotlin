package kotlin_1.`7`

//Kotlin 1.7.0 introduces an underscore operator, _, for type arguments.
// You can use it to automatically infer a type argument when other types are specified.

abstract class SomeClass<T> {
    abstract fun execute():T
}

class StringImplementation: SomeClass<String>() {
    override fun execute(): String = "Test"
}

class IntImplementation: SomeClass<Int>() {
    override fun execute(): Int = 42
}

object Runner {
    inline fun <reified S: SomeClass<T>, T> run(): T {
        return S::class.java.getDeclaredConstructor().newInstance().execute()
    }
}

fun main() {

    // T is inferred as String because SomeImplementation derives from SomeClass<String>
    val s = Runner.run<StringImplementation,_>()
    println("S: $s")

    // T is inferred as Int because OtherImplementation derives from SomeClass<Int>
    val n = Runner.run<IntImplementation, _>()
    println("N: $n")

}