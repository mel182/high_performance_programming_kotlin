package wz_retrofit3_concept.parameter

import wz_retrofit3_concept.request.RequestBuilder

class Tag<T>: ParameterHandler<T>() {

    private var custom_class:Class<T>? = null
    fun setClass(_class:Class<T>): Tag<T> {
        this.custom_class = _class
        return this
    }

    override fun apply(builder: RequestBuilder, values: T?) {
        custom_class?.let {
            builder.addTag(it, value = values)
        }
    }
}