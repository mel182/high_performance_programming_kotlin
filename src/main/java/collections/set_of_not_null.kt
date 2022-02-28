package collections

fun main() {
     // setOfNotNull(), which makes a set consisting
    // of all the non-null items among the provided
    // arguments.
    val set = setOfNotNull(null,1,2,0,null)
    println("Set result: $set")
}