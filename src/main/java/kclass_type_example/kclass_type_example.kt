
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

fun main() {
    val myModel = foo(Model2::class,"test")
    val myModel2 = foo(Model3::class,"test")

    println("My model: ${myModel}")
    println("My model 2: ${myModel2}")
}

data class Model2(val name:String = "", val age:Int = 1) : BaseClass()
data class Model3(val name:String = "", val age:Int = 1) : BaseClass()

abstract class BaseClass{
    var test:String = ""

}

inline fun<reified T: BaseClass> foo(type: KClass<T>, name:String): T {
    val resourceModel = T::class.createInstance() as BaseClass
    resourceModel.test = name

    return when (type)
    {
        BaseClass::class -> resourceModel as T
        else -> T::class.createInstance()
    }
}