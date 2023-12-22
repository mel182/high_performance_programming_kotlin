package non_nullable_types

//You can mark a generic type parameter as definitely non-nullable at
// the use site with the new syntax T & Any. The syntactic form comes
// from the notation for intersection types and is now limited to a
// type parameter with nullable upper bounds on the left side of &
// and a non-nullable Any on the right side:

//fun <T> elvisLike(x: T, y: T & Any): T & Any = x ?: y

fun <T> elvisLike(x: T, y: T & Any): T & Any = x ?: y

fun main() {

    val test1 = elvisLike<String>("x","y")
    println("test1 : $test1") // x since 'x' is never null, so it always return 'x'

    // Error: 'null' cannot be a value of a non-null type
    // val test2 = elvisLike<String>("x",null)

    val test3 = elvisLike<String?>(null,"y")
    println("test3 : $test3") // y since this time 'x' is null

    // Error: 'null' cannot be a value of a non-null type
    // val test4 = elvisLike<String?>(null,null)
}