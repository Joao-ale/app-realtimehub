package com.realtimehub.domain.user.entity

import java.time.Instant

class RefreshToken(
    val id: String,
    val userId: String,
    val tokenHash: String,
    val expiresAt: Instant,
    val revoked: Boolean,
    val createdAt: Instant,
)
