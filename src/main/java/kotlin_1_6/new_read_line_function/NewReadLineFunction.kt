package kotlin_1_6.new_read_line_function

// Kotlin 1.6.0 offers new functions for handling standard input: readln() and readlnOrNull().
// Earlier versions  | 1.6.0 alternative | Usage
// readLine()!!      | readln()          | Reads a line from stdin and returns it, or throws a RuntimeException if EOF has been reached.
// readLine()        | readlnOrNull()    | Reads a line from stdin and returns it, or returns null if EOF has been reached.
//
// We believe that eliminating the need to use !! when reading a line will improve the experience for newcomers and simplify
// teaching Kotlin. To make the read-line operation name consistent with its println() counterpart, we've decided to shorten
// the names of new functions to 'ln'.
//
// The existing readLine() function will get a lower priority than readln() and readlnOrNull() in your IDE code completion.
// IDE inspections will also recommend using new functions instead of the legacy readLine().
// We're planning to gradually deprecate the readLine() function in future releases.
fun main() {
    println("What is your nickname?")
    val nickname = readln()
    println("Hello, $nickname")
}