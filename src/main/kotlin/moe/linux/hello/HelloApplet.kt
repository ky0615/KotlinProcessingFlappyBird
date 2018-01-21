package moe.linux.hello

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import moe.linux.hello.di.AppModule
import org.jbox2d.collision.AABB
import org.jbox2d.collision.PolygonShape
import org.jbox2d.collision.ShapeDef
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.World
import org.slf4j.Logger
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PFont

open class HelloApplet : KodeinAware, PApplet() {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
    }

    private val logger: Logger = kodein.instance()

    val font: PFont by lazy { createFont("Baskerville-48", 24f, true) }

    var text: TextBase = TextA()

    var color = 0

    companion object {
        fun main() {
            println("start processing")
//            PApplet.main(HelloApplet::class.java)
            PApplet.main(HelloApplet3::class.java)
        }
    }

    override fun setup() {
        super.setup()
        frameRate(60f)
        logger.info("load font: ${font.name}")
        textFont(font)
        surface.setResizable(true)
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


class HelloApplet3 : HelloApplet() {
    override fun setup() {
        super.setup()
//        text = TextB()
    }

    override fun settings() {
        super.settings()
    }

    override fun draw() {
//        super.draw()
        ellipse(200F, 200F, 380F, 380F)
    }
}

open class AppletCore : KodeinAware, PApplet() {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
    }

    private val logger: Logger = kodein.instance()

    lateinit var world: World

    override fun setup() {
        super.setup()
        logger.info("setup")
        frameRate(60f)
    }

    override fun settings() {
        super.settings()
        logger.info("setting")
        size(400, 400)
        val gravity = Vec2(0F, 9.8F)
        val world = World(AABB(), gravity, false)

        val groundBody = world.createBody(
                BodyDef().apply {
                    this.position.set(0F, 0F)
                })
        val groundBox = PolygonShape(ShapeDef()).also {
        }
    }

    override fun draw() {
        fill(204F, 90F)
        rect(0F, 0F, width.toFloat(), height.toFloat())
        fill(0F, 102F)
        ellipse(mouseX.toFloat(), mouseY.toFloat(), 9F, 9F)
    }
}

fun main(args: Array<String>): Unit {
    PApplet.main(AppletCore::class.java)
}