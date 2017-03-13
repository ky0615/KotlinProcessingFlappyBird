package ws.temp.quizzly.model

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import ws.temp.quizzly.di.AppModule
import ws.temp.quizzly.di.ModelModule
import ws.temp.quizzly.normalizeText
import ws.temp.quizzly.splitText
import kotlin.test.assertEquals

class LineModelTest : KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
        import(ModelModule())
    }

    val lineModel: LineModel = kodein.instance()

    val logger: Logger = kodein.instance()

    @Before
    fun before() {

    }

    @Test
    fun normalizeText() {

//        assertEquals("ﾊﾝｶｸｶﾅ".normalizeText(), "ハンカクカナ")
        assertEquals("ﾊﾝｶｸｶﾅ".normalizeText(), "はんかくかな")
        assertEquals("ＡＢＣ　ａｂｃ".normalizeText(), "ABC abc")
        assertEquals("１２３".normalizeText(), "123")
        assertEquals("ひらがな漢字".normalizeText(), "ひらがな漢字")
    }

    @Test
    fun splitText() {
        assertEquals("".normalizeText().splitText(), emptyList())
        assertEquals("a 　b  a".normalizeText().splitText().size, 3)
        assertEquals("クイズ code start".normalizeText().splitText().size, 3)
    }
}
