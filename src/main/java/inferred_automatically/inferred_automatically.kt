package inferred_automatically

fun main() {
    val test = rulesMap.getValue("weak")("abc")
    println(test)
}

val rulesMap: Map<String, (String?) -> Boolean> = mapOf(
        "weak" to { it != null },
        "medium" to { !it.isNullOrBlank() },
        "strong" to { it != null}
)