package wz_retrofit3_concept.parameter

import wz_retrofit3_concept.request.RequestBuilder

class Tag<T>(private val _class:Class<T>): ParameterHandler<T>() {

    override fun apply(builder: RequestBuilder, values: T?) {
        builder.addTag(_class, value = values)
    }
}