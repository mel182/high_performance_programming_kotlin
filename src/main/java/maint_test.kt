import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.util.*


fun main() {


    //1632393265393

    val millis = 1632391268255 - System.currentTimeMillis()

    val startCalendar = Calendar.getInstance()
    startCalendar.timeInMillis = 1632393265393

    val endCalendar = Calendar.getInstance()

    val difference = endCalendar.time.time - startCalendar.time.time
//    val minuteDifference = (difference / (1000 * 60) % 60)
    println("difference: $difference")
    val minuteDifference = difference / (60 * 1000) % 60

//    val minutes2 = TimeUnit.MILLISECONDS.toMinutes(difference)

    /*
    long diffSeconds = diff / 1000 % 60;
long diffMinutes = diff / (60 * 1000) % 60;
long diffHours = diff / (60 * 60 * 1000);
    */

    println("current millis: ${endCalendar.time.time}")

    println("minute: $minuteDifference")
//    println("minute 2: $minutes2")


//    val startCalendar = Calendar.getInstance()
//    startCalendar.time.time = 1632391268255
//
//    val endCalendar = Calendar.getInstance()
//
//    val startDate = startCalendar.time
//    val endDate = endCalendar.time

//    val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
    /*
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
    val startDate = simpleDateFormat.parse("22:00:59")
    val endDate = simpleDateFormat.parse("23:00:10")
    */

//    var difference = endDate.time - startDate.time
//    if (difference < 0) {
//        val dateMax = simpleDateFormat.parse("24:00:00")
//        val dateMin = simpleDateFormat.parse("00:00:00")
//        difference = dateMax.time - startDate.time + (endDate.time - dateMin.time)
//    }
//    val days = (difference / (1000 * 60 * 60 * 24)).toInt()
//    val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
//    val min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
//    val sec = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours - 1000 * 60 * min).toInt() / 1000
//
//
//    println("Hours: $hours, Mins: $min, Secs: $sec")

    val testValue = "test"
    val testBooleanValue = true
    require(testValue.isNotBlank() && testBooleanValue) { "Test is not blank" }

    try {

        /*
        return this?.let { raw_json ->

    try {
        val inputStream: InputStream = ByteArrayInputStream(raw_json.toByteArray())
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonReader = JsonReader(inputStreamReader)
        var result: Match
        jsonReader.use {
            result = Gson().fromJson(jsonReader, Match::class.java)
        }
        result
    }catch (e: JsonSyntaxException)
    {
        Match()
    }
}?: Match()

        */

        // ,"key1":"value1"
        val rawData = "{\"data\":[{ \"key1\":\"value1\",\"key2\":\"value2\"}]}"
        val inputStream: InputStream = ByteArrayInputStream(rawData.toByteArray())
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonReader = JsonReader(inputStreamReader)
        jsonReader.use {

            val result = Gson().fromJson<TestResponse123>(jsonReader, TestResponse123::class.java)
            println("Result 1: $result")

//            val result2 = Gson().fromJson<TestResponse1234>(jsonReader, TestResponse123::class.java)
//            println("Result 2: $result2")
        }

        val testObject = TestResponse1234(data = listOf(
                hashMapOf(
                        "key1" to "value1",
                        "key2" to "value2"
                )
        ))

        val jsonString = Gson().toJson(testObject)
        println("json string: $jsonString")

    }catch (e:Exception) {
        println("error parsing json data cause: ${e.message}")
    }

    /*
    Byte sent: 4088
2023-07-09 15:33:57.764 12787-12787 TAG88                   com.http.retrofitktx                 I  Content length: 2992484
    */


    val calTest12 = 4088L * 1.00
    println("Cal test 12: $calTest12")

    val calTest1 = 4088L

//    val decFormat = DecimalFormat(".##")
//    val result = decFormat.format(calTest1)
    //val result = String.format("%.2f", calTest1)

    val result =  "%.2f".format(calTest1.toDouble()).toDouble()


    println("Cal test 1: ${result}")
    val byteSent = 4088L * 1.00
    val contentLength = 2992484L * 1.00
    val calTest = (byteSent/contentLength) * 100.00
    println("Cal test: $calTest")
    /*
    val random = 8435.21057752090819915

    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
    val roundoff = df.format(random)
    println(roundoff)
    */

}

data class TestResponse123(@SerializedName("data") val data:List<Map<String,String>>)
data class TestResponse1234(val data:List<Map<String,String>>)

