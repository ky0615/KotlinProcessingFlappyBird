package moe.linux.hello.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import moe.linux.hello.dao.Command
import moe.linux.hello.dao.CommandName
import moe.linux.hello.dao.Configure
import moe.linux.hello.dao.EnvParameter
import moe.linux.hello.normalizeText
import java.io.File
import java.util.*


fun AppModule() = Kodein.Module {
    bind<Logger>() with provider { LoggerFactory.getLogger("main") }

    bind<ObjectMapper>() with singleton { ObjectMapper().registerModule(KotlinModule()) }

    bind<Configure>() with singleton {
        val env: EnvParameter = instance()
        val objectMapper: ObjectMapper = instance()

        objectMapper.readValue(File("./${env.env}.json").readBytes(), Configure::class.java)
    }

    bind<List<Command>>() with singleton {
        val objectMapper: ObjectMapper = instance()
        try {
            objectMapper.readValue<List<Command>>(File("./command.json").readBytes(),
                    objectMapper.typeFactory.constructCollectionType(List::class.java, Command::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Command>()
        }
    }

    bind<HashMap<String, CommandName>>() with singleton {
        val commandBase: List<Command> = instance()
        commandBase
                .flatMap { command -> command.command.map { Pair(it.normalizeText(), command.name) } }
                .toMap(HashMap())
    }

    bind<EnvParameter>() with singleton {
        EnvParameter.parseOf(System.getenv("QUIZZLY_ENV").orEmpty())
    }
}
