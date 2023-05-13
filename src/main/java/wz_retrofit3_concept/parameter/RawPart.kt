package wz_retrofit3_concept.parameter

import okhttp3.MultipartBody
import wz_retrofit3_concept.request.RequestBuilder

class RawPart(val builder: RequestBuilder? = null, val value: MultipartBody.Part? = null): ParameterHandler<MultipartBody.Part>() {

    val INSTANCE = RawPart()

    override fun apply(builder: RequestBuilder, values: MultipartBody.Part?) {
        values?.let {
            builder.addPart(it)
        }
    }
}