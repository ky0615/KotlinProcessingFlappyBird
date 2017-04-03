package moe.linux.hello.util

import moe.linux.hello.toHexString

fun createToken(): String {
    return System.nanoTime().toHexString()
}