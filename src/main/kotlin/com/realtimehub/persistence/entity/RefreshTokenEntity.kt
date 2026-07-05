package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "refresh_tokens")
class RefreshTokenEntity(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID? = null,

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    var userId: UUID = UUID.randomUUID(),

    @Column(name = "token_hash", nullable = false, unique = true)
    var tokenHash: String = "",

    @Column(name = "expires_at", nullable = false)
    var expiresAt: Instant = Instant.now(),

    @Column(nullable = false)
    var revoked: Boolean = false,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),
)
