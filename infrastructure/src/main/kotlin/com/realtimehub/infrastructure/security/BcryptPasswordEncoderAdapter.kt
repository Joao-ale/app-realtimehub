package com.realtimehub.infrastructure.security

import com.realtimehub.domain.port.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class BcryptPasswordEncoderAdapter : PasswordEncoder {
    private val encoder = BCryptPasswordEncoder()

    override fun encode(rawPassword: String): String = encoder.encode(rawPassword)

    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        encoder.matches(rawPassword, encodedPassword)
}
