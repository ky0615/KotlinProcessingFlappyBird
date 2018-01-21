package moe.linux.hello


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.slf4j.Logger
import processing.core.PApplet


fun main(args: Array<String>) {
    PApplet.main(HelloApplet3::class.java)
}

fun Logger.toJson(obj: Any) {
    info("\n" + ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .writeValueAsString(obj))
}

fun Long.toHexString(): String {
    return java.lang.Long.toHexString(this)
}
