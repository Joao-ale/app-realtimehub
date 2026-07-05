package com.realtimehub.service

import java.time.Instant
import java.util.UUID

data class RefreshTokenMetadata(
  val jti: String,
  val userId: UUID,
  val issuedAt: Instant,
  val expiresAt: Instant,
  val deviceId: String? = null,
  val ip: String? = null,
  val userAgent: String? = null,
  val revoked: Boolean = false
)
