package wz_retrofit3_concept

import com.custom.http.client.annotation.http.call_properties.Body
import wz_retrofit3_concept.annotation.http.method.GET
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.platform.Platform
import wz_retrofit3_concept.services.ServiceMethod
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap


fun main() {
    println("test main")

    val builder = Retrofit3.Companion.Builder(title = "Title test", image = "Image test", url = "URL test")

    val retrofit3 = Retrofit3(builder)

    println("Image: ${retrofit3.getBuilder().image}")
    println("Title: ${retrofit3.getBuilder().title}")
    println("Url: ${retrofit3.getBuilder().url}")
    val test = retrofit3.create(EndPointTest::class.java)
    //"test",1
    println("test -> test: ${test.test(body = "Test")}")
}

interface EndPointTest {

    //test:String, age:Int
    //@Body body:String
    @GET("/book/all")
    fun test(@Body body:String): String

}

class Retrofit3(private val builder:Builder) {

    private val serviceMethodCache: ConcurrentHashMap<Method, ServiceMethod<*>> = ConcurrentHashMap()

    val converterFactories: List<Converter.Factory> = ArrayList()

    fun getBuilder(): Builder = builder

    fun <T> create(service: Class<T>) : T {


//        val lookUpTest = MethodHandles.lookup()
//        lookUpTest.unreflectSpecial()
//
//
//        return (service as ParameterizedType) as T

        return Proxy.newProxyInstance(
                service.classLoader,
                arrayOf<Class<*>>(service),
                object: InvocationHandler {

                    private val emptyArgs = arrayOf<Any>(0)
                    private val platform = Platform.get()

                    override fun invoke(proxy: Any?, method: Method, args: Array<Any>?): Any {

                        // If the method is a method from Object then defer to normal invocation.
                        if (method?.declaringClass == Any::class.java) {
                            return method.invoke(this, args)
                        }

                        val _args = args ?: emptyArgs
                        return if (platform.isDefaultMethod(method = method))
                            platform.invokeDefaultMethod(method = method, declaringClass = service, proxy = proxy, args = _args)
                        else
                            loadServiceMethod(method = method).invoke(_args)!!
                    }

                }
        ) as T
    }

    companion object {
        class Builder(@JvmField val title:String = "", @JvmField val image:String = "", @JvmField val url:String = "") {

        }
    }

    fun loadServiceMethod(method: Method): ServiceMethod<*> =  serviceMethodCache[method]?.let {
        it
    }?:run {
        synchronized(serviceMethodCache) {
            val _result = ServiceMethod.parseAnnotations2(this, method)
            serviceMethodCache[method] = _result!!
            _result
        }
    }

}