package moe.linux.hello.dao

enum class EnvParameter(val env: String) {
    DEVELOPMENT("development"),
    PRODUCTION("production");

    companion object {
        fun parseOf(name: String): EnvParameter {
            return values()
                    .firstOrNull { it.env == name } ?: DEVELOPMENT
        }
    }
}
