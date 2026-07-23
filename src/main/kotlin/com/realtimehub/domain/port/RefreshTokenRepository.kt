package com.realtimehub.domain.port

import com.realtimehub.domain.user.entity.RefreshToken
import java.util.UUID

interface RefreshTokenRepository {
    fun save(refreshToken: RefreshToken): RefreshToken
    fun findByTokenHash(tokenHash: String): RefreshToken?
    fun revokeAllByUserId(userId: UUID)
    fun deleteExpired()
}
