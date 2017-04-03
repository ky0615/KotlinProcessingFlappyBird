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

class HelloApplet : KodeinAware, PApplet() {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
    }

    private val logger: Logger = kodein.instance()

    private val config: Configure = kodein.instance()

    companion object {
        fun main() {
            println("start processing")
            PApplet.main(HelloApplet::class.java)
        }
    }

    override fun settings() {
        super.settings()
        logger.info("setting")
        size(400, 800, PConstants.P3D)
    }

    override fun setup() {
        super.setup()
        logger.info("setup")
    }

    override fun draw() {
        super.draw()
        logger.info("draw")
        background(0f, 128f, 0f)
        translate(width/2f, height/2f, -150f)
        (frameCount/100f).also {
            rotateX(it)
            rotateY(it)
            box(200f)
        }


    }
}
