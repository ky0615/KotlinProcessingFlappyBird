package moe.linux.hello.dao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Configure(
        val prefix: String = "/api",

        val port: Int = 4201,

        val url: String = "http://quizzly.local"
)
