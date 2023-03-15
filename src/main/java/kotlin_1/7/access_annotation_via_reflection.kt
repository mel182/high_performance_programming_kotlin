@file:OptIn(ExperimentalStdlibApi::class)

package kotlin_1.`7`

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.full.findAnnotations

// The KAnnotatedElement.findAnnotations() extension function,
// which was first introduced in 1.6.0, is now Stable.
// This reflection function returns all annotations of a given
// type on an element, including individually applied and repeated annotations.

@Repeatable
annotation class Tag(val name: String)

@Tag("First Tag")
@Tag("Second Tag")
fun taggedFunction() {
    println("I'm a tagged function!")
}

fun main() {
    val x = ::taggedFunction
    val foo = x as KAnnotatedElement
    println(foo.findAnnotations<Tag>()) // [@kotlin_1.7.Tag(name="First Tag"), @kotlin_1.7.Tag(name="Second Tag")]
}