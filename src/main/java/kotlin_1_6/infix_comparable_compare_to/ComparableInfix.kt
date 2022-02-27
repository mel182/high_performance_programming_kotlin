package kotlin_1_6.infix_comparable_compare_to

// Version 1.6.0 added the infix extension function for Comparable.compareTo().
// You can now use the infix form for comparing two objects for order.
fun main() {

    val list = arrayListOf(TestObject("Melchior3",24),
            TestObject("Melchior2",26),
            TestObject("Melchior1",26))
    list.sort()

    for (testObject in list) {
        println("name: ${testObject.name} and age: ${testObject.age}")
    }
}

class TestObject(val name:String, val age:Int) : Comparable<TestObject> {

    override fun compareTo(other: TestObject): Int {
        return this.name compareTo other.name
    }
}