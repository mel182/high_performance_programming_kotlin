package inline_value_class_delegation

//If you want to create a lightweight wrapper for a value or class instance,
// it's necessary to implement all interface methods by hand. Implementation
// by delegation solves this issue, but it did not work with inline classes
// before 1.7.0. This restriction has been removed, so you can now create
// lightweight wrappers that do not allocate memory in most cases.

interface Bar {
    fun foo() = "foo"
}

@JvmInline
value class BarWrapper(val bar: Bar): Bar by bar

fun main() {
    println("Main function called!")
    val bw = BarWrapper(object : Bar {
        override fun foo(): String {
            return "foo 2"
        }
    })
    println(bw.foo())
}


