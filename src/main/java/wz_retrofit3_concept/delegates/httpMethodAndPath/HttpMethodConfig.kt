package wz_retrofit3_concept.delegates.httpMethodAndPath

import com.custom.http.client.constant.BLANK_STRING
import wz_retrofit3_concept.constant.*

sealed class HttpMethodConfig {
    data class Delete(private val _value:String): HttpConfigWrapper(value = _value, httpMethod = DELETE)
    data class Get(private val _value:String): HttpConfigWrapper(value = _value, httpMethod = GET)
    data class Head(private val _value:String): HttpConfigWrapper(value = _value, httpMethod = HEAD)
    data class Path(private val _value:String): HttpConfigWrapper(value = _value, httpMethod = PATCH, hasBody = true)
    data class Post(private val _value:String): HttpConfigWrapper(value = _value, httpMethod = POST, hasBody = true)
    data class Put(private val _value:String): HttpConfigWrapper(value = _value, httpMethod = PUT, hasBody = true)
    data class Options(private val _value:String): HttpConfigWrapper(value = _value, httpMethod = OPTIONS)
    data class Http(private val _httpMethod:String, private val _value:String, private val methodHasBody:Boolean): HttpConfigWrapper(value = _value,httpMethod = _httpMethod, hasBody = methodHasBody)
    data class None(private val _value:String = BLANK_STRING): HttpConfigWrapper(value = _value, httpMethod = BLANK_STRING)
}