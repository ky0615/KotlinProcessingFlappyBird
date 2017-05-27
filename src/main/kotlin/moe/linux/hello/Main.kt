package moe.linux.hello


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.slf4j.Logger


fun main(args: Array<String>) {
    HelloApplet.main()
}

fun Logger.toJson(obj: Any) {
    info("\n" + ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .writeValueAsString(obj))
}

fun Long.toHexString(): String {
    return java.lang.Long.toHexString(this)
}
