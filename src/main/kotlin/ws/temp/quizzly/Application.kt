package ws.temp.quizzly

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import org.slf4j.Logger
import org.wasabifx.wasabi.app.AppConfiguration
import org.wasabifx.wasabi.app.AppServer
import org.wasabifx.wasabi.deserializers.JsonDeserializer
import org.wasabifx.wasabi.protocol.http.StatusCodes
import ws.temp.quizzly.dao.Configure
import ws.temp.quizzly.dao.EnvParameter
import ws.temp.quizzly.dao.ErrorResponse
import ws.temp.quizzly.di.AppModule
import ws.temp.quizzly.exception.UserException
import ws.temp.quizzly.exception.UserNotFoundException
import ws.temp.quizzly.util.RawDeserializer

class Application : KodeinAware {

    override val kodein: Kodein by Kodein.lazy {
        import(AppModule())
    }

    private val logger: Logger = kodein.instance()

    private val config: Configure = kodein.instance()

    private val server: AppServer

    init {
        logger.info("env param: ${kodein.instance<EnvParameter>().env}")

        server = AppServer(AppConfiguration(port = config.port)).apply {
            deserializers.removeIf { it is JsonDeserializer }
            deserializers.add(RawDeserializer())

            exception {
                response.setStatus(when (exception) {
                    is UserNotFoundException -> StatusCodes.NotFound
                    is UserException -> StatusCodes.BadRequest
                    else -> StatusCodes.InternalServerError
                })
                response.sendJson(ErrorResponse(exception))
            }

            get("/", {
                response.send("Hello World")
            })
        }

        server.start()
    }
}
