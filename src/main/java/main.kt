import Kotlin_1_4.break_continue_when_statement.test

fun main() {

    println("main function called!")

    val items = arrayListOf<String>("top","item1","item2","item3","bottom")


    val updatedItems = arrayListOf<String>("item12","item13","item14")

    items.addAll(updatedItems)

    for (itemFound in items) {
        println("item found: ${itemFound}")
    }

//    val testString = updatedItems.joinToString(",")
    val testString = ""
    println(testString)

    //println(testString.contains(null))

    val item = ListItemTest12(name = "John")

    val wrapper = ListItemTestWrapper()
    item.let(wrapper::setTestListItem)

}


data class ListItemTest12(val name: String)

class ListItemTestWrapper {

    fun setTestListItem(item: ListItemTest12) {
        println("list item passed: $item")
    }


}