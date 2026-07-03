package com.realtimehub.infrastructure.persistence.repository

import com.realtimehub.infrastructure.persistence.entity.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.Instant
import java.util.UUID

interface JpaRefreshTokenRepository : JpaRepository<RefreshTokenEntity, UUID> {
    fun findByTokenHash(tokenHash: String): RefreshTokenEntity?

    @Modifying
    @Query("UPDATE RefreshTokenEntity r SET r.revoked = true WHERE r.userId = :userId")
    fun revokeAllByUserId(userId: UUID)

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.expiresAt < :now")
    fun deleteExpired(now: Instant)
}
