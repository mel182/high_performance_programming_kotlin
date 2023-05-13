package wz_retrofit3_concept.platform

import wz_retrofit3_concept.call.CompletableFutureCallAdapterFactory
import wz_retrofit3_concept.call.DefaultCallAdapterFactory
import wz_retrofit3_concept.getSystemPlatform
import wz_retrofit3_concept.interfaces.CallAdapter
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodHandles.Lookup
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.util.concurrent.Executor


open class Platform(private val hasJava8Types:Boolean = false) {


    private var lookupConstructor: Constructor<Lookup>? = null

    init {
        this.lookupConstructor = try {
            Lookup::class.java.getDeclaredConstructor(Class::class.java, Int::class.javaPrimitiveType).also {
                it.isAccessible = true
            }
        } catch (ignored: NoClassDefFoundError) {
            null
        } catch (ignored: NoSuchMethodException) {
            null
        }
    }

    companion object {

        @JvmStatic
        private val DALVIK = "Dalvik"

        @JvmStatic
        private val PLATFORM:Platform = retrievePlatform()

        @JvmStatic
        private fun retrievePlatform(): Platform = when(getSystemPlatform()) {
            DALVIK -> Android()
            else -> Platform(hasJava8Types = true)
        }

        fun get(): Platform = retrievePlatform()
    }

    open fun defaultCallbackExecutor(): Executor? = null

    open fun defaultCallAdapterFactories(callbackExecutor: Executor?): List<CallAdapter.Factory> {
        val executorFactory = DefaultCallAdapterFactory(callbackExecutor)
        return if (hasJava8Types) listOf(CompletableFutureCallAdapterFactory.INSTANCE, executorFactory) else listOf(executorFactory)
    }

    open fun defaultCallAdapterFactoriesSize(): Int = if (hasJava8Types) 2 else 1

    open fun defaultConverterFactoriesSize(): Int = if (hasJava8Types) 1 else 0

    open fun isDefaultMethod(method: Method?): Boolean = hasJava8Types && method?.isDefault == true

    @Throws(Throwable::class)
    open fun invokeDefaultMethod(method: Method?, declaringClass: Class<*>?, proxy: Any?, vararg args: Any?): Any {
        val lookup = lookupConstructor?.newInstance(declaringClass, -1) ?: MethodHandles.lookup()
        return lookup.unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args)
    }

}