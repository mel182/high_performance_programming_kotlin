package deep_recursive

// Deep recursive functions have been available as an experimental
// feature since Kotlin 1.4.0, and they are now Stable in Kotlin 1.7.0.
// Using DeepRecursiveFunction, you can define a function that keeps its
// stack on the heap instead of using the actual call stack.
// This allows you to run very deep recursive computations.
// To call a deep recursive function, invoke it.

class Tree(val left: Tree?, val right: Tree?)

val calculateDepth = DeepRecursiveFunction<Tree?, Int> { t ->
    if (t == null) 0 else maxOf(
        callRecursive(t.left),
        callRecursive(t.right)
    ) + 1
}

fun main() {

//    In this example, a deep recursive function is used to calculate the depth
//    of a binary tree recursively. Even though this sample function calls itself
//    recursively 100,000 times, no StackOverflowError is thrown:

    // Generate a tree with a depth of 100_000
    val deepTree = generateSequence(Tree(null, null)) { prev ->
        Tree(prev, null)
    }.take(100_000).last()

    println(calculateDepth(deepTree)) // 100000

}