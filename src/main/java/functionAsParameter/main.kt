package functionAsParameter

fun main() {

    println("Main function called!")
    mainFunction()

}

fun mainFunction(){
    testFunction("key passed", ::functionTest)
}

inline fun testFunction(key:String, bar: (params:String) -> Unit) {
    bar(key)
}

fun functionTest(params:String) {
    println("Function test: $params")
}


