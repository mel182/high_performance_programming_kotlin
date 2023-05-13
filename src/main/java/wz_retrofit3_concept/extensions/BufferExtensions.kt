package wz_retrofit3_concept.extensions

import okhttp3.internal.and
import okio.Buffer
import wz_retrofit3_concept.constant.HEX_DIGITS
import wz_retrofit3_concept.constant.PATH_SEGMENT_ALWAYS_ENCODE_SET

fun Buffer.canonicalizeForPath(input:String, position:Int, limit:Int, alreadyEncoded:Boolean) {

    var codePoint:Int
    run {
        var i: Int = position
        while (i < limit) {
            codePoint = input.codePointAt(i)
            if (alreadyEncoded
                    && (codePoint == '\t'.code || codePoint == '\n'.code || codePoint == '\u000c'.code || codePoint == '\r'.code)) {
                // Skip this character.
            } else if (codePoint < 0x20 || codePoint >= 0x7f || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint.toChar()) != -1 || !alreadyEncoded && (codePoint == '/'.code || codePoint == '%'.code)) {
                // Percent encode this character.
                val utf8Buffer = Buffer()
                utf8Buffer.writeUtf8CodePoint(codePoint)
                while (!utf8Buffer.exhausted()) {
                    val b: Int = utf8Buffer.readByte() and 0xff
                    this.writeByte('%'.code)
                    this.writeByte(HEX_DIGITS[b shr 4 and 0xf].code)
                    this.writeByte(HEX_DIGITS[b and 0xf].code)
                }
            } else {
                // This character doesn't need encoding. Just copy it over.
                this.writeUtf8CodePoint(codePoint)
            }
            i += Character.charCount(codePoint)
        }
    }
}
