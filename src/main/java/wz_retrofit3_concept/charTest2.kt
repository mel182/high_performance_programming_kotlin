package wz_retrofit3_concept

import android.R.id
import wz_retrofit3_concept.constant.PATH_SEGMENT_ALWAYS_ENCODE_SET


fun main() {


    val charValue: Char = 'A'
    val charValue2 = 20
    val charValue3 = 7f

//    val code = 0x20
//    val uni = String.format("u+%04x", code)
//    println("unicode: ${uni}")
    println("code: ${charValue.code}")
    println("code 2: ${charValue2.toChar()}")
    println("code 3: ${charValue3.toInt().toChar()}")

    val rawValue = DataTest("raw value 1")
    rawValue.modifyData()

    println("raw value: $rawValue")

    return

    val input = "This is the test value"
    var codePoint = 0
    run {
        var i = 0
        val limit = input.length
        while (i < limit) {
            codePoint = input.codePointAt(i)

//            if (codePoint < 0x20 || codePoint >= 0x7f || PATH_SEGMENT_ALWAYS_ENCODE_SET.indexOf(codePoint) !== -1 || !alreadyEncoded && (codePoint === '/' || codePoint === '%')) {
//                // Slow path: the character at i requires encoding!
//                val out = Buffer()
//                out.writeUtf8(id.input, 0, i)
//                canonicalizeForPath(out, id.input, i, limit, alreadyEncoded)
//                return out.readUtf8()
//            }


            println("index: $i")
            println("code point: $codePoint")
            println("character char count: " + Character.charCount(codePoint))
            i += Character.charCount(codePoint)
        }
    }
}

data class DataTest(var value:String)

private fun DataTest.modifyData() {
    this.value = "1234"
}
