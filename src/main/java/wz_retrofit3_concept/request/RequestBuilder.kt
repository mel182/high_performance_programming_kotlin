package wz_retrofit3_concept.request

import com.custom.http.client.constant.BLANK_STRING
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import wz_retrofit3_concept.extensions.canonicalizeForPath
import wz_retrofit3_concept.extensions.isContentType
import java.util.regex.Pattern

class RequestBuilder(val method:String, val baseUrl:HttpUrl, private var _relativeUrl:String? = null, val headers: Headers? = null, var contentType: MediaType? = null, val hasBody:Boolean, val isFormEncoded:Boolean, val isMultipart:Boolean) {

    // Matches strings that contain . or .. as a complete path segment. This also matches dots in their percent-encoded form, %2E.
    // It is okay to have these strings within a larger path segment (like a..z or index.html) but when alone they
    // have a special meaning. A single dot resolves to no path segment so /one/./three/ becomes /one/three/. A double-dot pops
    // the preceding directory, so /one/../three/ becomes /three/.
    // We forbid these in Retrofit paths because they're likely to have the unintended effect. For example, passing ..
    // to DELETE /account/book/{isbn}/ yields DELETE /account/.
    private val PATH_TRAVERSAL: Pattern = Pattern.compile("(.*/)?(\\.|%2e|%2E){1,2}(/.*)?")
    private var relativeUrl:String? = _relativeUrl

    private val requestBuilder = Request.Builder()
    private var urlBuilder = HttpUrl.Builder()
    private val formBuilder: FormBody.Builder? = if (isFormEncoded) FormBody.Builder() else null
    private val multipartBuilder: MultipartBody.Builder? = if (!isFormEncoded && isMultipart) MultipartBody.Builder().apply { setType(MultipartBody.FORM) } else null
    private val headerBuilder = headers?.newBuilder() ?:run {
        Headers.Builder()
    }
    private var body: RequestBody? = null

    internal fun setRelativeUrl(relativeUrl:Any) {
        this.relativeUrl = relativeUrl.toString()
    }

    internal fun addHeader(name:String, value:String) {
        if (name.isContentType()) {
            try {
                contentType = value.toMediaType()
            }catch (e:IllegalArgumentException){
                throw java.lang.IllegalArgumentException("Malformed content type: $value",e)
            }
        } else {
            headerBuilder.add(name = name, value = value)
        }
    }

    internal fun addHeaders(headers:Headers) {
        headerBuilder.addAll(headers)
    }

    internal fun addPathParams(name:String, value:String, encoded:Boolean) {

        relativeUrl?.let {_url ->

            val replacement = value.canonicalizeForPath(alreadyEncoded = encoded)
            val newRelativeUrl = _url.replace("{$name}",replacement)
            require(!PATH_TRAVERSAL.matcher(newRelativeUrl).matches()) { "@Path parameters shouldn't perform path traversal ('.' or '..'): $value" }
            relativeUrl = newRelativeUrl
        }?:run {
            throw AssertionError() // The relative URL is cleared when the first query parameter is set.
        }
    }

    internal fun addQueryParam(name:String, value:String? = null, encoded:Boolean) {

        relativeUrl?.let {
            // Do a one-time combination of the built relative URL and the base URL.
            val oneTimeCombinationBuilder = baseUrl.newBuilder(it)
            requireNotNull(oneTimeCombinationBuilder) { "Malformed URL. Base: $baseUrl, Relative: $relativeUrl" }
            urlBuilder = oneTimeCombinationBuilder
            relativeUrl = null
        }

        urlBuilder.apply {
            if (encoded) {
                //noinspection ConstantConditions Checked to be non-null by above 'if' block.
                addEncodedQueryParameter(encodedName = name, encodedValue = value)
            } else {
                //noinspection ConstantConditions Checked to be non-null by above 'if' block.
                addQueryParameter(name = name, value = value)
            }
        }
    }

    // Only called when isFormEncoded was true.
    internal fun addFormField(name:String, value:String, encoded:Boolean) {

        formBuilder?.apply {

            if (encoded) {
                addEncoded(name = name, value = value)
            } else {
                add(name = name, value = value)
            }
        }
    }

    // Only called when isMultipart was true.
    internal fun addPart(headers:Headers, body:RequestBody) {
        multipartBuilder?.addPart(headers = headers, body = body)
    }

    // Only called when isMultipart was true.
    internal fun addPart(part: MultipartBody.Part) {
        multipartBuilder?.addPart(part = part)
    }

    internal fun setBody(body:RequestBody) {
        this.body = body
    }

    internal fun <T> addTag(cls: Class<T>, value: T?) {
        requestBuilder.tag<T>(cls, value)
    }

    fun get(): Request.Builder {
        val url:HttpUrl = this.urlBuilder.build()
        var body:RequestBody? = this.body ?: run {
            formBuilder?.build()
                ?: (multipartBuilder?.build()
                ?: if (hasBody) {
                    // Body is absent, make an empty body.
                    BLANK_STRING.toRequestBody(null)
                } else null)
        }

        val contentType = this.contentType

        contentType?.let {

            if (body != null) {
                body = ContentTypeOverridingRequestBody(requestBody = body, contentType = contentType)
            } else {
                headerBuilder.add("Content-Type", contentType.toString())
            }
        }

        return requestBuilder.url(url).headers(headerBuilder.build()).method(method, body)
    }

    private inner class ContentTypeOverridingRequestBody(private val requestBody:RequestBody?, private val contentType:MediaType?): RequestBody() {

        override fun contentType(): MediaType? = contentType

        override fun contentLength(): Long = requestBody?.contentLength() ?: 0L

        override fun writeTo(sink: BufferedSink) {
            requestBody?.writeTo(sink)
        }

    }
}