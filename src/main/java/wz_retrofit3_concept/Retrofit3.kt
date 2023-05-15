package wz_retrofit3_concept

import com.custom.http.client.annotation.http.call_properties.Body
import com.custom.http.client.constant.BLANK_STRING
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import wz_retrofit3_concept.annotation.http.method.GET
import wz_retrofit3_concept.converter.BuiltInConverter
import wz_retrofit3_concept.converter.Converter
import wz_retrofit3_concept.interfaces.CallAdapter
import wz_retrofit3_concept.platform.Platform
import wz_retrofit3_concept.services.ServiceMethod
import java.lang.IllegalStateException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor


fun main() {
    println("test main")

    val builder = Retrofit3.Builder()

    val retrofit3 = Retrofit3(builder)

//    println("Image: ${retrofit3.getBuilder().image}")
//    println("Title: ${retrofit3.getBuilder().title}")
//    println("Url: ${retrofit3.getBuilder().url}")
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

    var callFactory: Call.Factory? = null
        private set

    var baseUrl: HttpUrl? = null
        private set

    val converterFactories: List<Converter.Factory> = ArrayList()
    val callAdapterFactories: List<CallAdapter.Factory> = ArrayList()
    val callbackExecutor: Executor? = null
    var validateEagerly: Boolean = false
        private set

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

    class Builder {

        // @JvmField val title:String = "", @JvmField val image:String = "", @JvmField val url:String = ""

        private var platform:Platform? = null
        private var callFactory: Call.Factory? = null
        private var baseUrl: HttpUrl? = null
        private val converterFactories: ArrayList<Converter.Factory> = ArrayList()
        private val callAdapterFactories: ArrayList<CallAdapter.Factory> = ArrayList()
        private var callbackExecutor: Executor? = null
        private var validateEagerly: Boolean = false

        constructor(): this(Platform.get())

        constructor(platform: Platform) {
            this.platform = platform
        }

        constructor(retrofit3: Retrofit3) {
            this.platform = Platform.get()
            this.callFactory = retrofit3.callFactory
            this.baseUrl = retrofit3.baseUrl


            platform?.let {

                // Do not add the default BuiltIntConverters and platform-aware converters added by build().
                val converterFactoryListSize: Int = retrofit3.converterFactories.size - it.defaultConverterFactoriesSize()
                for (index in 0 until converterFactoryListSize) {
                    converterFactories.add(retrofit3.converterFactories[index])
                }

                // Do not add the default, platform-aware call adapters added by build().
                val callAdapterFactoryListSize: Int = retrofit3.callAdapterFactories.size - it.defaultConverterFactoriesSize()
                for (index in 0 until callAdapterFactoryListSize) {
                    callAdapterFactories.add(retrofit3.callAdapterFactories[index])
                }
            }

            this.callbackExecutor = retrofit3.callbackExecutor
            this.validateEagerly = retrofit3.validateEagerly
        }

        fun client(client:OkHttpClient?): Builder = callFactory(requireNotNull(client){ "client == null" })

        fun callFactory(factory:Call.Factory?): Builder {
            this.callFactory = requireNotNull(factory) { "client == null" }
            return this
        }

        fun baseUrl(baseUrl:URL?): Builder {
            requireNotNull(baseUrl){ "baseUrl == null" }
            return baseUrl(baseUrl.toString().toHttpUrl())
        }

        fun baseUrl(baseUrl:String?): Builder {
            requireNotNull(baseUrl){ "baseUrl == null" }
            return baseUrl(baseUrl.toHttpUrl())
        }

        fun baseUrl(baseUrl:HttpUrl?): Builder {
            requireNotNull(baseUrl){ "baseUrl == null" }
            val pathSegments = baseUrl.pathSegments
            require(pathSegments.last() != BLANK_STRING) { "baseUrl must end in /: $baseUrl" }
            this.baseUrl = baseUrl
            return this
        }

        fun addConverterFactory(factory: Converter.Factory?): Builder {
            converterFactories.add(requireNotNull(factory){ "factory == null" })
            return this
        }

        fun addCallAdapterFactory(factory: CallAdapter.Factory?): Builder {
            this.callAdapterFactories.add(requireNotNull(factory){ "factory == null" })
            return this
        }

        fun callbackExecutor(executor: Executor?): Builder {
            this.callbackExecutor = requireNotNull(executor){ "executor == null" }
            return this
        }

        fun validateEagerly(validateEagerly: Boolean): Builder {
            this.validateEagerly = validateEagerly
            return this
        }

        fun build(): Retrofit3 {

            if (baseUrl == null)
                throw IllegalStateException("Base URL required")

            val callFactory = this.callFactory ?: OkHttpClient()
            val callbackExecutor = this.callbackExecutor ?: platform?.defaultCallbackExecutor()

            // Make a defensive copy of the adapters and add the default Call adapter.
            val callbackFactories = this.callAdapterFactories
            platform?.defaultCallAdapterFactories(callbackExecutor)?.let {
                callbackFactories.addAll(it)
            }

            // Make a defensive copy of the converters.
            val converterFactories = ArrayList<Converter.Factory>().apply {
                add(BuiltInConverter())
                addAll(this@Builder.converterFactories)
                platform?.defaultConverterFactories()?.let {
                    addAll(it)
                }
            }

            return Retrofit3(this)
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