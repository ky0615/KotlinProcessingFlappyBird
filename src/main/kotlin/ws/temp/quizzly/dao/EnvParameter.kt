package ws.temp.quizzly.dao

enum class EnvParameter(val env: String) {
    DEVELOPMENT("development"),
    PRODUCTION("production");

    companion object {
        fun parseOf(name: String): EnvParameter {
            return EnvParameter.values()
                    .firstOrNull { it.env == name } ?: DEVELOPMENT
        }
    }
}
