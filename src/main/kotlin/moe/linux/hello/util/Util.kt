package moe.linux.hello.util

import moe.linux.hello.toHexString
import processing.core.PApplet

fun createToken(): String {
    return System.nanoTime().toHexString()
}

fun PApplet.fill(r: Int, g: Int, b: Int) = this.fill(r.toFloat(), g.toFloat(), b.toFloat())

fun PApplet.textSize(size: Int) = this.textSize(size.toFloat())

fun PApplet.text(str: String, x: Int, y: Int) = this.text(str, x.toFloat(), y.toFloat())

fun PApplet.rect(x: Int, y: Int, width: Int, height: Int) = this.rect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())



