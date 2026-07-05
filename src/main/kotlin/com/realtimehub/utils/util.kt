package com.realtimehub.utils

import com.realtimehub.exception.InvalidPasswordException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID

fun generateId(): String {
	return UUID.randomUUID().toString()
}

fun generatePasswordHash(password: String): String {
	val encoder = BCryptPasswordEncoder(16)
	return encoder.encode(password)
}

fun validatePassword(password: String, userPassword: String) : Boolean{
	val encoder = BCryptPasswordEncoder(16)
	val isMatch = encoder.matches(password, userPassword)

	if (!isMatch) throw InvalidPasswordException()

	return isMatch
}