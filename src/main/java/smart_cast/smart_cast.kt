package smart_cast

fun main() {
    println(result)
}

//In Kotlin 1.4, thanks to the new inference algorithm,
// the last expression inside a lambda gets smart cast,
// and this new, more precise type is used to infer the
// resulting lambda type. Thus, the type of the result
// variable becomes String.
val result = run {

    var str:String? = null
    if (str == null)
        str = "test"
    str  // the Kotlin compiler knows that str is not null here

}
// The type of 'result' is String? in Kotlin 1.3 and String in Kotlin 1.4