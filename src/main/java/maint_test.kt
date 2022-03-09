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
}