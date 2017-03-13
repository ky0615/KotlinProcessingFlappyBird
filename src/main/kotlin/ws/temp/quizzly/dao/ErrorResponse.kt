package ws.temp.quizzly.dao

import ws.temp.quizzly.exception.BaseUserException
import ws.temp.quizzly.exception.UserException

data class ErrorResponse(
        val errors: List<Error>
) {
    constructor(throwable: Throwable) : this(arrayListOf(Error(throwable as? BaseUserException ?: UserException(throwable))))
}

data class Error(
        val message: String = "",
        val code: String = ""
) {
    constructor(throwable: Throwable) : this(throwable.message.orEmpty(), "")

    constructor(throwable: BaseUserException) : this(throwable.message.orEmpty(), throwable.code.toString())
}
