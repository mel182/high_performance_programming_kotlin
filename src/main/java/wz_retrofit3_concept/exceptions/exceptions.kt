package wz_retrofit3_concept.exceptions

import java.lang.reflect.Method

fun throwMethodError(method:Method, cause: Throwable? = null, message:String, vararg args: Any?) {
    throw IllegalArgumentException("${String.format(message, *args)}\n   for method ${method.declaringClass.simpleName}.${method.name}",cause)
}

fun throwParameterError(method:Method, parameter:Int, message:String, vararg args: Any?) {
    throwMethodError(method = method, message = "$message (parameter #${parameter+1})", args = args)
}

fun throwParameterError(method:Method, cause:Throwable, parameter:Int, message:String, vararg args: Any?) {
    throwMethodError(method = method, cause = cause, message = "$message (parameter #${parameter+1})", args = args)
}