package com.realtimehub.domain.port

import java.util.UUID

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)

interface TokenProvider {
    fun generateAccessToken(userId: UUID, email: String): String
    fun generateRefreshToken(): String
    fun validateAccessToken(token: String): UUID?
    fun getAccessTokenExpiration(): Long
}
