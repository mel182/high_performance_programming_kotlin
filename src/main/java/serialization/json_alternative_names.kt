package serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/*
What if your API returns either:
    {"name":"string value"}
    or
    {"title":"string value"} ?
*/

@Serializable
data class JsonMapper(@JsonNames("title") val name:String)
// "name" or "title" -- either will work!