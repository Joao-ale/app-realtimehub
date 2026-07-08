package com.realtimehub.shared.utils

import com.realtimehub.interfaces.exception.InvalidPasswordException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object PasswordUtils {

	fun validatePassword(password: String, userPassword: String) : Boolean{
		val encoder = BCryptPasswordEncoder(16)
		val isMatch = encoder.matches(password, userPassword)

		if (!isMatch) throw InvalidPasswordException("Invalid password")

		return isMatch
	}

	fun generatePasswordHash(password: String): String {
		val encoder = BCryptPasswordEncoder(16)
		return encoder.encode(password)
	}
}