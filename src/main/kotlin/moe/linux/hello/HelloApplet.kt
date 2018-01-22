package moe.linux.hello

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseCredentials
import com.google.firebase.database.FirebaseDatabase
import moe.linux.hello.util.fill
import moe.linux.hello.util.rect
import moe.linux.hello.util.text
import moe.linux.hello.util.textSize
import processing.core.PApplet
import java.util.*

val SPACE_OFFSET = 250
val BAR_LENGTH = 3

class FlappyBird : PApplet() {

    val game = Game()
    val ranking = Ranking()

    var page = Page.GAME

    override fun setup() {
        super.setup()
        surface.setResizable(true)
        frameRate(60f)
        background(0)

        game.setup(this)
        firebaseInit()
    }

    override fun settings() {
        super.settings()
        size(500, 700)
    }

    override fun draw() {
        when (page) {
            Page.GAME -> {
                game.update(this)
            }

            Page.RANKING -> {
                ranking.update(this)
            }
        }
    }

    override fun mousePressed() {
        super.mousePressed()
        game.jump()
    }

    class Game {
        var x = 0F
        var y = 0F
        var h = 0F

        var end = false
        var isPause = false

        var score = 0
        var highscore = 0

        var keyPressedCache = false

        lateinit var bar: List<Game.Bar>

        fun setup(app: FlappyBird) = with(app) {
            bar = (0 until BAR_LENGTH).map { Bar(it, { score++ }, { gameOver(this) }) }
        }

        fun update(app: FlappyBird) = with(app) {
            if (keyPressed && !keyPressedCache) {
//                kotlin.io.println("keyPressed: $key")
                when (key) {
                    'r' -> reset()
                    'p' -> setPause()
                    'k' -> exit()
                    'g' -> {
                        if (end) {
                            app.page = Page.RANKING
                        }
                    }
                    ' ' -> {
                        if (end) {
                            reset()
                        } else
                            jump()
                    }
                }
            }
            keyPressedCache = keyPressed

            if (end || isPause) return

            background(0)
            textSize(50)

            // draw myself
            fill(0, 0, 255)
            rect(250, (height / 2 + h).toInt(), 40, 40)

            fill(255)
            bar.forEach { it.update(this, h) }

            x += y
            if (y < 30) y++
            h = x / 4
            if (y > 40) y = 40F
            if (y < -30) y = -30F

//            if (score >= highscore) highscore = score

            fill(255)
            text(score.toString(), 10, 50)
        }

        fun jump() {
            y -= 50
        }

        fun reset() {
            x = 0F
            y = 0F
            h = 0F
            end = false
            score = 0

            bar.forEach { it.reset() }
        }

        fun gameOver(app: FlappyBird) = with(app) {
            app.ranking.score = score
            textSize(90)
            fill(255, 0, 0)
            text("GameOver", 10, 300)

            textSize(50)
            text("highscore: ${if (highscore < score) score else highscore}", 10, 400)

            if (highscore < score)
                text("Press 'G' key,\nregistration\nthe ranking", 10, 490)

            if (score >= highscore) highscore = score

            end = true
        }

        fun setPause() {
            isPause = !isPause
        }

        class Bar(val num: Int = 0, val addScoreListener: () -> Unit = {}, val gameOverListener: () -> Unit = {}) {
            val offset = 250 * (num + 2)

            var holeH = 0
            var holeD = 0

            var move = 0

            val coodinate: Int
                get() = offset - move

            fun update(app: PApplet, h: Float) = with(app) {
                if (move == 1) randHole(this)

                val hx = height / 2 + h

                fill(0, 0, 255)
                rect(coodinate, 0, 75, holeH)
                rect(coodinate, holeD, 75, height)

                if (coodinate in 166..289)
                    if (hx < holeH || hx > holeD - 40)
                        gameOverListener()

                if (move == SPACE_OFFSET * (num + 1))
                    addScoreListener()

                move = if (move == offset + 225) SPACE_OFFSET * num * (BAR_LENGTH - 2) else move + 1

//                kotlin.io.println("num: $num move: $move")
//                if (num == BAR_LENGTH - 1) kotlin.io.println("")
            }

            /**
             * 穴の大きさを定義する
             */
            private fun randHole(app: PApplet) = with(app) {
                holeH = random(0F, 500F).toInt()
                holeD = holeH + 180
            }

            fun reset() {
                move = 0
            }
        }
    }

    class Ranking {
        var inputNow = false
        var score = 0
        fun update(app: FlappyBird) = with(app) {
            if (!inputNow) {
                inputNow = true
                println("ハイスコアを登録するために名前を入力してください\n> ")
                val scanner = Scanner(System.`in`)
                val name = scanner.next()
                ranking(Score(name, score))
                println("your name: $name, score: ${score}")
                app.page = Page.GAME
                app.game.reset()
                inputNow = false
            }
        }
    }

    enum class Page {
        GAME,
        GAME_OVER,
        RANKING,
    }
}


fun firebaseInit() {
    val resource = ClassLoader.getSystemResourceAsStream("serviceAccountKey.json")
    val options = FirebaseOptions.Builder()
            .setCredential(FirebaseCredentials.fromCertificate(resource))
            .setDatabaseUrl("https://api-project-488342428656.firebaseio.com")
            .build()

    FirebaseApp.initializeApp(options)
}

fun ranking(score: Score): Unit {
    FirebaseDatabase.getInstance()
            .getReference("/flappybird/ranking")
            .push()
            .setValueAsync(score)
    println("data pushed ")

}


data class Score(val name: String, val score: Int, val data: Long = Date().time)
