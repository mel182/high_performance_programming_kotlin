package Kotlin_1_5

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


// This is powerful new feature to convert kotlin object to JSON.
// To use it add the following dependencies:
// - id 'org.jetbrains.kotlin.plugin.serialization' version '1.5.10' in build.gradle -> plugin
// - implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1" in dependencies
fun main() {
    println("Main function called!")

    val data = UserSerialization("Melchior",35)
    val jsonString = Json.encodeToString(data)
    println("Json string: $jsonString") // {"name":"Melchior","age":35}

    val userSerializationObject = Json.decodeFromString<UserSerialization>(jsonString)
    println("User serialization: $userSerializationObject") // UserSerialization(name=Melchior, age=35)

    // Using value classes to encode to string
    val data2 = UserPreference("Melchior",35, UserColor("Blue"))
    val jsonString2 = Json.encodeToString(data2)
    println("Json string 2: $jsonString2") // {"name":"Melchior","age":35,"color":"Blue"}

    val userSerializationObject2 = Json.decodeFromString<UserPreference>(jsonString2)
    println("User serialization 2: $userSerializationObject2") // UserPreference(name=Melchior, age=35, color=UserColor(color=Blue))

    // Unsigned integers example
    val counted = 239.toUByte()
    println("Counted: $counted")

    val jsonStringUByte = Json.encodeToString(Counter(counted = counted,"tries"))
    println("Json string U byte: $jsonStringUByte") // {"counted":239,"description":"tries"}

    // ULong value class example
    println("Big counter result: ${Json.encodeToString(BigCounter(ULong.MAX_VALUE))}") // {"counted":18446744073709551615}
}

@Serializable
data class UserSerialization(val name:String, val age:Int)

// Value class example
@Serializable
data class UserPreference(val name:String, val age:Int, val color:UserColor)

@Serializable
@JvmInline
value class UserColor(val color:String)

// Unsigned integers example
@Serializable
class Counter(val counted: UByte, val description:String)

@Serializable
data class BigCounter(val counted: ULong)
