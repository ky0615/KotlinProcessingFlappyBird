package moe.linux.hello

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import moe.linux.hello.dao.Configure
import moe.linux.hello.di.AppModule
import org.slf4j.Logger
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PFont

open class HelloApplet : KodeinAware, PApplet() {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
    }

    private val logger: Logger = kodein.instance()

    private val config: Configure = kodein.instance()

    val font: PFont by lazy { createFont("Baskerville-48", 24f, true) }

    var text: TextBase = TextA()

    var color = 0

    companion object {
        fun main() {
            println("start processing")
            PApplet.main(HelloApplet::class.java)
            PApplet.main(HelloApplet2::class.java)
        }
    }

    override fun setup() {
        super.setup()
        frameRate(60f)
        logger.info("load font: ${font.name}")
        textFont(font)
        frame.isResizable = true
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

interface TextBase {
    fun paint(color: Int): PApplet.() -> Unit;
}

class TextA : TextBase {
    override fun paint(color: Int): PApplet.() -> Unit = {
        fill(color)
        textMode(PConstants.SHAPE)
        textSize(24f)
        textWidth("")
        textAlign(PConstants.CENTER, PConstants.CENTER)
        text("hogeほげホゲ歩下", width / 2f, height / 2f)
    }
}

class TextB : TextBase {
    override fun paint(color: Int): PApplet.() -> Unit = {
        fill(color)
        textMode(PConstants.SHAPE)
        textSize(24f)
        textWidth("")
        textAlign(PConstants.CENTER, PConstants.CENTER)
        text("22222", width / 2f, height / 2f)
    }

}

class HelloApplet2 : HelloApplet() {
    override fun setup() {
        super.setup()
        text = TextB()

    }

    override fun settings() {
        super.settings()
    }

    override fun draw() {
        super.draw()
    }
}