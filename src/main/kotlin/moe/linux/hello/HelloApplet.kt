package moe.linux.hello

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import moe.linux.hello.di.AppModule
import moe.linux.hello.util.fill
import moe.linux.hello.util.rect
import moe.linux.hello.util.text
import moe.linux.hello.util.textSize
import org.slf4j.Logger
import processing.core.PApplet

open class HelloApplet : KodeinAware, PApplet() {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
    }

    val logger: Logger = kodein.instance()
}

interface ClassBase {
    fun update(): PApplet.() -> Unit
}

class HelloApplet3 : HelloApplet() {
    var x = 0.toFloat()
    var y = 0.toFloat()
    var h = 0.toFloat()

    var move1 = 0
    var move2 = 0
    var move3 = 0

    var end = false
    var score = 0
    var highscore = 0

    var aa = 0.toFloat()
    var ab = 0.toFloat()
    var ba = 0.toFloat()
    var bb = 0.toFloat()
    var ca = 0.toFloat()
    var cb = 0.toFloat()

    var keyPressedCache = false

    override fun setup() {
        super.setup()
        surface.setResizable(true)
        frameRate(60f)
        background(0)
    }

    override fun settings() {
        super.settings()
        size(500, 700)
    }

    override fun draw() {
        if (keyPressed && !keyPressedCache) {
            kotlin.io.println("keyPressed: $key")
            when (key) {
                'r' -> reset()
                ' ' -> {
                    if (end)
                        reset()
                    else
                        jump()
                }
            }
        }
        keyPressedCache = keyPressed

        if (end) return

        background(0)
        textSize(50)

        // draw myself
        fill(0, 0, 255)
        rect(250, (height / 2 + h).toInt(), 40, 40)

        if (move1 == 1) {
            aa = random(0F, 500F)
            ab = aa + 180
        }

        fill(255)
        fill(0, 0, 255)
        rect(500 - move1, 0, 75, aa.toInt())
        rect(500 - move1, ab.toInt(), 75, height)

        if (500 - move1 in 166..289)
            if (height / 2 + h < aa || height / 2 + h > ab - 40)
                gameOver()

        move1 = if (move1 == 725) 0 else move1 + 1

        if (move1 == 250 || move2 == 500) score++

        if (move2 == 1) {
            ba = random(0F, 500F)
            bb = ba + 180
        }

        fill(255)
        fill(0, 255, 0)
        rect(750 - move2, 0, 75, ba.toInt())
        rect(750 - move2, bb.toInt(), 75, height)

        if (750 - move2 in 166..289)
            if (height / 2 + h < ba || height / 2 + h > bb - 40)
                gameOver()

        if (move2 == 975) {
            move2 = 250
            ba = random(0F, 500F)
            bb = ba + 180
        }
        move2++

        if (move2 == 750) score++

        if (move3 == 3) {
            ca = random(0F, 500F)
            cb = ca + 180
        }

        fill(255)
        fill(255, 0, 0)
        rect(1000 - move3, 0, 75, ca.toInt())
        rect(1000 - move3, cb.toInt(), 75, height)

        if (1000 - move3 in 166..289)
            if (height / 2 + h < ca || height / 2 + h > cb - 40)
                gameOver()

        if (move3 == 1225) {
            move3 = 500
            ca = random(0F, 500F)
            cb = ca + 180
        }
        move3++

        x += y
        if (y < 30) y++
        h = x / 4
        if (y > 40) y = 40F
        if (y < -30) y = -30F

        if (score >= highscore) highscore = score

        fill(255)
        text(score.toString(), 10, 50)
    }

    override fun mousePressed() {
        super.mousePressed()
        jump()
    }

    fun jump() {
        y -= 50
    }

    fun reset() {
        x = 0F
        y = 0F
        h = 0F
        move1 = 0
        move2 = 0
        move3 = 0
        end = false
        score = 0
    }

    fun gameOver() {
        textSize(90)
        fill(255, 0, 0)
        text("GameOver", 10, 300)

        textSize(50)
        text("highscore: ${highscore}", 10, 400)
        end = true
    }

    class Bar : ClassBase {
        var gameOverListener = {}

        var holeH = 0F
        var holeD = 0F

        override fun update(): PApplet.() -> Unit = {

        }

        /**
         * 穴の大きさを定義する
         */
        fun randHole(): PApplet.() -> Unit = {
            holeH = random(0F, 500F)
            holeD = holeH + 180
        }
    }
}