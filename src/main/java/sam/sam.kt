package sam

fun interface IntPredicate {
    fun accept(i: Int):Boolean
}

val isEven = IntPredicate { value ->
    value % 2 == 0
}

fun main(){
    println("Is 7 even? ${isEven.accept(7)}")
}