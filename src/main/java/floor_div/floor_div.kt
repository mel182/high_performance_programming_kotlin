package floor_div

// New operations for modular arithmetics have been added to the standard library.
// - FloorDiv
// - mod
// These operations look quite similar to the existing division of integers and 'rem()'
// function (or the % operator), but they work differently on negative numbers.
fun main() {

    // floorDiv: differs from a regular '/' in that floorDiv rounds the result down
    //           (towards the lesser integer), whereas '/' truncates the result to
    //           the integer closer to 0.
    val floorDivTest = (-5).floorDiv(3)
    println("floor div: ${floorDivTest}") // -2

    val truncatedDivision = -5 /3
    println("truncated division: ${truncatedDivision}")

    // mod: is the difference between 'a' and 'a.floorDiv(b) * b'. It’s either zero or has the same
    //      sign as 'b', while 'a % b' can have a different one.
    val modulus = (-5).mod(3)
    println("Modulus: $modulus") // 1

    val remainder = -5 % 3
    println("Remainder: $remainder") // -2
}