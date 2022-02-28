package Kotlin_1_5

import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.listDirectoryEntries

// The experimental Path API with extensions for 'java.nio.file.Path' is now Stable.
fun main() {

    // construct path with the div (/) operator
    val baseDir = Path("/base")
    val subDir = baseDir / "subdirectory"

    println("sub directory: ${subDir}")

    // list files in a directory
    val kotlinFiles : List<Path> = Path("/home/user").listDirectoryEntries("*.kt")
    println("kotlin files list: $kotlinFiles")

}