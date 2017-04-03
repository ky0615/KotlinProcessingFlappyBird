package moe.linux.hello

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import moe.linux.hello.dao.Configure
import moe.linux.hello.di.AppModule
import org.slf4j.Logger
import processing.core.PApplet

class HelloApplet : KodeinAware, PApplet() {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
    }

    private val logger: Logger = kodein.instance()

    private val config: Configure = kodein.instance()

    val text = TextA()

    var color = 0

    companion object {
        fun main() {
            println("start processing")
            PApplet.main(HelloApplet::class.java)
        }
    }

    override fun settings() {
        logger.info("setting")
        size(400, 800)
    }

    override fun draw() {
        background(0f, 128f, 0f)
        if (color > 255)
            color = 0

        apply(text.paint(color++))
    }
}

class TextA {
    fun paint(color: Int): PApplet.() -> Unit = {
        fill(color)
        textSize(24f)
        text("hogeほげホゲ歩下", 10f, 200f)
    }
}
