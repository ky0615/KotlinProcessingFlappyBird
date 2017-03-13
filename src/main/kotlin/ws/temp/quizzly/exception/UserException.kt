package ws.temp.quizzly.exception

abstract class BaseUserException(override val message: String, open val code: CODE) : IllegalArgumentException(message) {

}

open class UserException(override val message: String, code: CODE = CODE.OTHER) : BaseUserException(message, code) {
    constructor(throwable: Throwable) : this(throwable.message ?: "Cause not known error.")
}

class UserNotFoundException : UserException("Not found the uid.", CODE.NOT_FOUND_UID)
class UserUidInvalidException(throwable: Throwable) : UserException(throwable.message ?: "Invalid the uid.", CODE.INVALID_UID)

enum class CODE(val code: Int) {
    OTHER(0),
    NOT_FOUND_UID(1),
    INVALID_UID(2),
    ;

    override fun toString(): String {
        return code.toString()
    }
}
