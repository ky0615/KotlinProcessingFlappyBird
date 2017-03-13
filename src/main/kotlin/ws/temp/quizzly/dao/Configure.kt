package ws.temp.quizzly.dao

data class Configure(
        val prefix: String = "/api",

        val port: Int = 4201,

        val url: String = "http://quizzly.local"
)
