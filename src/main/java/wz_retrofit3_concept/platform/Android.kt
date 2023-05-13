package wz_retrofit3_concept.platform

import android.os.Build
import android.os.Handler
import android.os.Looper
import java.lang.reflect.Method
import java.util.concurrent.Executor

class Android : Platform(hasJava8Types = Build.VERSION.SDK_INT >= 24) {

    override fun defaultCallbackExecutor(): Executor = MainThreadExecutor()

    override fun invokeDefaultMethod(method: Method?, declaringClass: Class<*>?, proxy: Any?, vararg args: Any?): Any {

        if (Build.VERSION.SDK_INT < 26)
            throw UnsupportedOperationException("Calling default methods on API 24 and 25 is not supported")

        return super.invokeDefaultMethod(method, declaringClass, proxy, *args)
    }

    inner class MainThreadExecutor: Executor {

        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            handler.post(command)
        }
    }
}