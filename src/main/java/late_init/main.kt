package late_init

class Main {

    // Alternative for using late init to prevent to much overheat
    private lateinit var name:String

    fun onCreate()
    {
        name = "Melchior"
        name.also {
            println(it)
        }
    }
    // ------------------------------------------------------------

}