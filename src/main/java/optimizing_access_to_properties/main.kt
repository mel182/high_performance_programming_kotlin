package optimizing_access_to_properties

fun main() {

    val testObject = StudentClass("Melchior",33)
    println("Test object: $testObject")
    println("Foo value: $Foo") // Calling the const val in 'EfficientConstantDeclaration.kt'
    println("Foo value: $Foo2") // Calling the const val in 'EfficientConstantDeclaration.kt'
}

// Since normally fields in Data classes are public and has a getter and setter.
// That's not exactly what you want (getter and setter are bad practice in Android since it has impact on compile time).
// To work around this, we can use the '@JvmField' annotation.
data class StudentClass(@JvmField val name:String, @JvmField val age:Int)