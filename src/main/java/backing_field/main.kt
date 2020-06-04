package backing_field

fun main() {

    val btn = Button()
    btn.text = "Button"

    btn.printText()



}

class Button {

    private var _text:String? = null

    var text:String
    set(value) {
        println(value)
//        field = value
        _text = value
    }
    get() {
        return _text+_text
    }

    var backgroundColor:Int? = null

    var onClickListener: ((Button) -> Unit)? = null

    fun printText(){
        println(_text)
    }

}