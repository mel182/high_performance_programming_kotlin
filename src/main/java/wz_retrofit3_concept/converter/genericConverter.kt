package wz_retrofit3_concept.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import wz_retrofit3_concept.Retrofit3
import java.lang.reflect.Type


fun <T> requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, converterFactories:List<Converter.Factory>,retrofit:Retrofit3): Converter<T, RequestBody>? {
    return nextRequestBodyConverter(null, type, parameterAnnotations, methodAnnotations,converterFactories,retrofit)
}

fun <T> nextRequestBodyConverter(skipPast: Converter.Factory?, type: Type?, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, converterFactories:List<Converter.Factory>, retrofit:Retrofit3): Converter<T, RequestBody>? {

    requireNotNull(type) { "type == null" }

    val start: Int = converterFactories.indexOf(skipPast) + 1
    run {
        var index = start
        val count: Int = converterFactories.size
        while (index < count) {
            val factory: Converter.Factory = converterFactories[index]
            val converter: Converter<*, RequestBody>? = factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
            if (converter != null) {
                return converter as Converter<T, RequestBody>?
            }
            index++
        }
    }
    val builder: StringBuilder = StringBuilder("Could not locate RequestBody converter for ").append(type).append(".\n")
    if (skipPast != null) {
        builder.append("  Skipped:")
        for (i in 0 until start) {
            builder.append("\n   * ").append(converterFactories[i]::class.java.name)
        }
        builder.append('\n')
    }
    builder.append("  Tried:")
    var i = start
    val count: Int = converterFactories.size
    while (i < count) {
        builder.append("\n   * ").append(converterFactories[i]::class.java.name)
        i++
    }
    throw IllegalArgumentException(builder.toString())
}


fun <T> nextResponseBodyConverter(skipPast: Converter.Factory?, type: Type?, annotations: Array<Annotation>, converterFactories:List<Converter.Factory>, retrofit:Retrofit3): Converter<ResponseBody?, T>? {

    requireNotNull(type) {"type == null"}

    val start: Int = converterFactories.indexOf(skipPast) + 1
    run {
        var i = start
        val count: Int = converterFactories.size
        while (i < count) {
            val converter: Converter<ResponseBody, *>? = converterFactories[i].responseBodyConverter(type, annotations, retrofit)
            if (converter != null) {
                return converter as Converter<ResponseBody?, T>
            }
            i++
        }
    }
    val builder = java.lang.StringBuilder("Could not locate ResponseBody converter for ")
            .append(type)
            .append(".\n")
    if (skipPast != null) {
        builder.append("  Skipped:")
        for (i in 0 until start) {
            builder.append("\n   * ").append(converterFactories[i]::class.java.name)
        }
        builder.append('\n')
    }
    builder.append("  Tried:")
    var i = start
    val count: Int = converterFactories.size
    while (i < count) {
        builder.append("\n   * ").append(converterFactories[i]::class.java.name)
        i++
    }
    throw java.lang.IllegalArgumentException(builder.toString())
}
