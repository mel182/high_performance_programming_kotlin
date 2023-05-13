package wz_retrofit3_concept.delegates

import okhttp3.Headers
import okhttp3.MediaType

data class MethodPathResult(val httpMethod:String? = null, val hasBody:Boolean? = null, val relativeUrl:String? = null, val relativeUrlParameters: Set<String>? = null, val headers: Headers? = null, val contentType: MediaType? = null, val isMultiPart: Boolean? = null, val isFormEncoded: Boolean? = null)