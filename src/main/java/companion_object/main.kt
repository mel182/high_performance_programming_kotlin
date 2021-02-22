
// We already know how top-level members and the @JvmField annotation work.
// The '@file:JvmName("Main")' annotation tells the compiler that all top-level
// members have to be contained in the Main class. And the '@JvmStatic' annotation
// tells the compiler that the 'value' property should be generated as static.

@file:JvmName("Main")
package companion_object

@JvmField
var previd = -1

class main {

    companion object {

        @JvmStatic
        private val value = 2
    }

}