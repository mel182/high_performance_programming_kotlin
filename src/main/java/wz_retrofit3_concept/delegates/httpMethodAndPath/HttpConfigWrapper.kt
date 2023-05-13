package wz_retrofit3_concept.delegates.httpMethodAndPath

import com.custom.http.client.constant.DEFAULT_BOOLEAN

abstract class HttpConfigWrapper(val value:String, val httpMethod:String, val hasBody:Boolean = DEFAULT_BOOLEAN): HttpMethodConfig()