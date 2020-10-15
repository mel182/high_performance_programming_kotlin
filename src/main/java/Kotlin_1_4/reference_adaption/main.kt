package Kotlin_1_4.reference_adaption


//Now you can adapt callable references to functions when passing a
//variable number of arguments (vararg) . You can pass any number
// of parameters of the same type at the end of the list of passed
// arguments.
fun main() {
    println("Main function called")

    use0(::foo)

}

fun foo(x:Int, vararg y:String){}

fun use0(f: (Int) -> Unit){}
fun use1(f: (Int,Unit) -> Unit){}
fun use2(f: (Int,Unit,String) -> Unit){}