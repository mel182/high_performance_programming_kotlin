package wz_retrofit3_concept.delegates

import okhttp3.Headers
import okhttp3.MediaType

data class HeaderParserResult(val headers:Headers, val contentType: MediaType? = null)
