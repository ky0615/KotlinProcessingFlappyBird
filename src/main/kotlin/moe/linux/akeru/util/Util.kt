package moe.linux.akeru.util

import moe.linux.akeru.toHexString

fun createToken(): String {
    return System.nanoTime().toHexString()
}