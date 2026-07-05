package com.realtimehub.exception

open class UserException (
	message: String,
	cause: Throwable? = null,
) : RuntimeException(message, cause)

class InvalidPasswordException() : UserException("Current password is invalid")