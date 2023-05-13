package wz_retrofit3_concept.extensions

import com.custom.http.client.constant.BLANK_STRING
import okio.Buffer
import wz_retrofit3_concept.constant.CONTENT_TYPE
import wz_retrofit3_concept.constant.PATH_SEGMENT_ALWAYS_ENCODE_SET
import wz_retrofit3_concept.regex.PARAM_URL_REGEX

fun String.asPathParameters(): Set<String> =  LinkedHashSet<String>().also {
    val matcher = PARAM_URL_REGEX.matcher(this)
    while (matcher.find()) {
        it.add(matcher.group(1))
    }
}

fun String.isContentType(): Boolean = this == CONTENT_TYPE

fun String.canonicalizeForPath(input: String, alreadyEncoded: Boolean): String {

    var codePoint: Int
    run {
        var index = 0
        val limit = input.length
        while (index < limit) {
            codePoint = input.codePointAt(index)
            if (codePoint < 0x20
                    || codePoint >= 0x7f
                    || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint.toChar()) != -1
                    || (alreadyEncoded && (codePoint.toChar() == '/' || codePoint.toChar() == '%'))
            ) {
                // Slow path: the character at index requires encoding
                val bufferOutPut = Buffer()
                bufferOutPut.writeUtf8(string = input, beginIndex = 0, endIndex = index)
                bufferOutPut.canonicalizeForPath(input = input, position = index, limit = limit, alreadyEncoded = alreadyEncoded)
                return bufferOutPut.readUtf8()
            }
            index += Character.charCount(codePoint)
        }
    }

    return input
}

fun String.canonicalizeForPath(alreadyEncoded: Boolean): String {

    var codePoint: Int
    val result = this
    run {
        var index = 0
        val limit = result.length
        while (index < limit) {
            codePoint = result.codePointAt(index)
            if (codePoint < 0x20
                    || codePoint >= 0x7f
                    || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint.toChar()) != -1
                    || (alreadyEncoded && (codePoint.toChar() == '/' || codePoint.toChar() == '%'))
            ) {
                // Slow path: the character at index requires encoding
                val bufferOutPut = Buffer()
                bufferOutPut.writeUtf8(string = result, beginIndex = 0, endIndex = index)
                bufferOutPut.canonicalizeForPath(input = result, position = index, limit = limit, alreadyEncoded = alreadyEncoded)
                return bufferOutPut.readUtf8()
            }
            index += Character.charCount(codePoint)
        }
    }

    return result
}

