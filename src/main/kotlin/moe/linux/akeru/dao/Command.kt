package moe.linux.akeru.dao

data class Command(
        var name: CommandName = CommandName.OTHER,
        var command: List<String>
)

enum class CommandName {
    RESET,
    SELECT,
    OK,
    NO,
    START_QUIZ,
    ANSWER_QUESTION,
    SHOW_MENU,
    SHOW_LOGOUT,
    LOGOUT,
    LOGIN,
    SHOW_POPULAR_QUIZ_LIST,
    SHOW_STATUS,
    OTHER
}
