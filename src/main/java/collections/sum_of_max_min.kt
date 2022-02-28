package collections

fun main() {
    val studentItemList = listOf(
            StudentItem("Student1",24),
            StudentItem("Student2",20),
            StudentItem("Student3",21),
            StudentItem("Student4",22),
            StudentItem("Student5",23),
            StudentItem("Student6",19),
            StudentItem("Student7",18),
            StudentItem("Student8",16),
    )

    //sumOf()
    val total = studentItemList.sumOf { it.age * 2 }
    val count = studentItemList.sumOf { it.age }

    println("total: ${total}")
    println("count: ${count}")

    // max of
    val maxOf = studentItemList.maxOf { it.age }

    // min of
    val minOf = studentItemList.minOf { it.age }

    println("max of ${maxOf}")
    println("min of ${minOf}")

}

data class StudentItem(val name:String, val age:Int)