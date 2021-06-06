package Kotlin_1_5

fun main() {

    // The new 'firstNotNullOf()' and 'firstNotNullOfOrNull()' functions combine
    // 'mapNotNull()' with 'first()' or 'firstOrNull()'. They map the original
    // collection with the custom selector function and return the first non-null value.

    val data = listOf("Kotlin","1.5")
    println(data.firstNotNullOf(String::toDoubleOrNull))
    println(data.firstNotNullOfOrNull(String::toIntOrNull))


    val nullDataList = listOf<String?>(null)

    //firstNotNullOfOrNull() returns null
    println("Result first not null of or null: ${nullDataList.firstNotNullOfOrNull { it }}") // null

    try {
        // If there is no such value, 'firstNotNullOf() throws an exception
        println("Result first not null: ${nullDataList.firstNotNullOf { it }}")
    } catch (e:Exception)
    {
        println("Error: ${e.message}") // Exception: No element of the collection was transformed to a non-null value
    }
}