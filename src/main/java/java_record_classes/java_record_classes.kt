package java_record_classes

/*
 * A Java Record in Java 15 is a new way to declare a type in the Java language.
 * Records were introduced to the Java language to reduce the boilerplate associated with Plain Old Java Objects (POJO).
 * When creating a good POJO, a developer must implement an equals method, a toString method, and the corresponding getters.
 *
 * |Java Record|                  VS               |Data class|
 *  No 'copy' method                               'copy' method for easier object creation
 *  Variable can only be final (val)               Variables can be 'var' or 'val'
 *  No inheritance                                 Can inherit from other non-data classes
 *  Can define only static variables               Can define non-constructor mutable variables
 */
fun main() {

    val student1 = StudentRecord("Melchior",24)
    val student2 = StudentRecord("Melchior2",24)

    println("Student 1: $student1")
    println("Student 2: $student2")

    println("Equal result: ${student1 == student2}")

}

@JvmRecord
data class StudentRecord(val name:String, val age:Int)