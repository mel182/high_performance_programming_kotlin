package type_classes.monad

// Monad is a typeclass that has a single function, flatMap, and represents a sequence of executions.

// Let's use a new feature in Kotlin called multiplatform projects to create a small example.
// First, create a standard Android project using Android Studio. Create the android directory and move the app module into it.
// All modules that relate to Android development will be located here.
// It's better to create a root build.gradle file with common configurations for all modules.

fun main() {
    println("Main function called!")

    val sets = listOf(
            getCategories(),
            getCategories()
    )

    println("without flatten: ${sets}")
    println("list flatten: ${sets.flatten()}")
}

fun getCategories() : List<Category>
{
    return listOf(
            Category(1,"Category 1"),
            Category(2,"Category 2"),
            Category(3,"Category 3"),
            Category(4,"Category 4")
    )
}
data class Category(val id:Int, val name:String)