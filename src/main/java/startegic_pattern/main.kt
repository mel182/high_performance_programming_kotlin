package startegic_pattern

import java.math.BigInteger
import javax.xml.crypto.Data

fun main() {

    val studentList = listOf(
            Student("Melchior","Vrolijk",32),
            Student("Melchior2","Vrolijk2",33),
            Student("Melchior3","Vrolijk3",34),
            Student("Melchior4","Vrolijk4",35)
    )

    // This approach decreased the readability and performance since every filter is a loop under the hood.
    // To solve this problem use composition function.
//    val filteredList = studentList.filter{
//        it.age in 33..34
//    }.filter {
//        it.lastName == "Vrolijk2"
//    }


    var testString:String? = null

    if (!testString.isNullOrBlank())
    {
        println("Value: $testString")
    } else {
        println("Value is blank or null")
    }



    // Composition solution
    val filteredList = studentList.filter{
        (it.age in 33..34).and(it.lastName == "Vrolijk2")
    }

    val eachStudent = studentList.forEach {

    }

    println("Filtered list: "+filteredList)

    val prices = listOf(3.4, 5.6, 5.6, 3.4, 3.4, 3.4, 5.6, 5.6, 3.4, 3.4)

//    fun discount(price:Price):Price = price.copy(total = (price.total * (price.discount/100)))
//    fun tax(price:Price):Price = price.copy(total = price.total + price.tax)
//        fun aid(price:Price):Int = price.tax



//    val test1 = ::discount
//    val test2 = ::aid

//    val total = ::discount + ::aid

//    val resultTotal = total(4)

//    println("resultTotal: $resultTotal")


    val data = listOf(DataList(listOf("a","b","c")),DataList(listOf("1","2","3")))

    println("Flat map example")
    val combined = data.flatMap { it.items }
    println(combined)
    println("End flat map example")
    println()
    println("Map example")
    val combinedMap = data.map { it.items }
    println(combinedMap)
    println("End map example")


    val lazyValue by lazy(LazyThreadSafetyMode.PUBLICATION) {
        println("Computation")
        BigInteger.valueOf(2).modPow(BigInteger.valueOf(7),BigInteger.valueOf(20))
    }

    println("Lazy value: $lazyValue")
    println("Lazy value: $lazyValue")

}

fun discount(price:Double) = price * (price/100)
fun tax(price:Double) = (price + (price * 2))
fun aid(price:Double) = price + 30
/*
fun discount(price: Double) = price - price * 0.9

    fun tax(price: Double) = price + 0.3

    fun aid(price: Double) = price - 0.1
*/

inline infix fun <P> ((P) -> Boolean).and(crossinline predicate: (P) -> Boolean): (P) -> Boolean {
    return { p: P -> this(p) && predicate(p) }
}

data class Student(val firstName:String, val lastName:String, val age:Int)
data class Price(val total:Int, val discount:Int, val tax:Int, val aid:Int)

data class DataList(val items:List<String>)