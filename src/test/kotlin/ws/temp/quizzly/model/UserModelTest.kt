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
import ws.temp.quizzly.toJson
import kotlin.test.assertNotEquals

class UserModelTest : KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
        import(ModelModule())
    }

    val userModel: UserModel = kodein.instance()

    val logger: Logger = kodein.instance()

    @Before
    fun before() {

    }

    @Test
    fun getAccountTest() {
        val user = userModel.getUser("Ue4a5804718f3bc99ca76dbd8b44bdcaa")
        logger.toJson(user ?: "")
        assertNotEquals(user, null)
    }
}