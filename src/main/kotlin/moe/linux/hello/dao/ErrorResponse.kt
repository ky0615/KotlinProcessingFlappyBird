package moe.linux.hello.dao

import moe.linux.hello.exception.BaseUserException
import moe.linux.hello.exception.UserException

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
