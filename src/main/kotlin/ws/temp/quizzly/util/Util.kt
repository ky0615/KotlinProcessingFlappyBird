package ws.temp.quizzly.util

import ws.temp.quizzly.toHexString

fun createToken(): String {
    return System.nanoTime().toHexString()
}