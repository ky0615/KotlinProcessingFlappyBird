package moe.linux.hello.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.provider
import org.slf4j.Logger
import org.slf4j.LoggerFactory


fun AppModule() = Kodein.Module {
    bind<Logger>() with provider { LoggerFactory.getLogger("main") }
}
