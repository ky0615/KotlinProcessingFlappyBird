package ws.temp.quizzly.util

import org.wasabifx.wasabi.deserializers.Deserializer
import java.util.*

class RawDeserializer : Deserializer("application/json") {
    override fun deserialize(input: Any): HashMap<String, Any> {
        return HashMap<String, Any>().apply { put("raw", input) }
    }

}