package collections.collection_builder

// In Kotlin 1.6.0, collection builder functions have been
// promoted to Stable. Collections returned by collection
// builders are now serializable in their read-only state.
//
// You can now use buildMap(), buildList(), and buildSet()
// without the opt-in annotation:
fun main() {
    val list = listOf('b','c')
    val listBuilder = buildList {  // It is now serializable in read-only state.
        add('a')
        addAll(list)
        add('d')
    }
    println(listBuilder)
}