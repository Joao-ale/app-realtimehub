package com.realtimehub.service

import com.realtimehub.security.JwtUtil
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.SetOperations
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.Duration
import java.util.*

@Service
class TokenService(
  private val jwtUtil: JwtUtil,
  private val redis: StringRedisTemplate
) {

  private fun refreshKey(jti: String) = "refresh:$jti"
  private fun userRefreshsKey(userId: UUID) = "user_refreshs:$userId"

  private fun hashOps(): HashOperations<String, String, String> = redis.opsForHash()
  private fun setOps(): SetOperations<String, String> = redis.opsForSet()

  fun createTokens(userId: UUID, deviceId: String? = null, ip: String? = null, userAgent: String? = null): TokenPair {
    val (access, accessJti) = jwtUtil.generateAccessToken(userId)
    val (refresh, refreshJti) = jwtUtil.generateRefreshToken(userId)

    val refreshClaims = jwtUtil.parseClaims(refresh)
    val issuedAt = refreshClaims.issuedAt.toInstant()
    val expiresAt = refreshClaims.expiration.toInstant()
    val seconds = Duration.between(Instant.now(), expiresAt).seconds

    val meta = mapOf(
      "jti" to refreshJti,
      "userId" to userId.toString(),
      "issuedAt" to issuedAt.toEpochMilli().toString(),
      "expiresAt" to expiresAt.toEpochMilli().toString(),
      "deviceId" to (deviceId ?: ""),
      "ip" to (ip ?: ""),
      "userAgent" to (userAgent ?: ""),
      "revoked" to "false"
    )

    // persist hash
    hashOps().putAll(refreshKey(refreshJti), meta)
    // add to user's set
    setOps().add(userRefreshsKey(userId), refreshJti)
    // set TTL on the hash key
    if (seconds > 0) {
      redis.expire(refreshKey(refreshJti), Duration.ofSeconds(seconds))
    }

    return TokenPair(access, accessJti, refresh, refreshJti)
  }

  fun validateRefreshToken(token: String): UUID? {
    if (!jwtUtil.validateToken(token)) return null
    if (jwtUtil.getType(token) != "refresh") return null
    val jti = jwtUtil.getJti(token)
    val data = hashOps().entries(refreshKey(jti))
    if (data == null || data.isEmpty()) return null
    val revoked = data["revoked"]
    if (revoked == "true") return null
    val userIdStr = data["userId"] ?: return null
    return try { UUID.fromString(userIdStr) } catch (_: Exception) { null }
  }

  fun revokeRefreshTokenByJti(jti: String) {
    val key = refreshKey(jti)
    val data = hashOps().entries(key)
    if (data != null && data.isNotEmpty()) {
      hashOps().put(key, "revoked", "true")
      // remove from user's set
      val userIdStr = data["userId"]
      if (!userIdStr.isNullOrBlank()) {
        try {
          val userId = UUID.fromString(userIdStr)
          setOps().remove(userRefreshsKey(userId), jti)
        } catch (_: Exception) { }
      }
    }
    // optionally keep key for audit until TTL expires
  }

  fun revokeRefreshToken(token: String) {
    val jti = jwtUtil.getJti(token)
    revokeRefreshTokenByJti(jti)
  }

  fun revokeAllRefreshTokensForUser(userId: UUID) {
    val key = userRefreshsKey(userId)
    val members = setOps().members(key) ?: emptySet()
    for (jti in members) {
      revokeRefreshTokenByJti(jti)
    }
    // remove the set index
    redis.delete(key)
  }

  fun listRefreshTokens(userId: UUID): List<RefreshTokenMetadata> {
    val key = userRefreshsKey(userId)
    val members = setOps().members(key) ?: emptySet()
    val result = mutableListOf<RefreshTokenMetadata>()
    for (jti in members) {
      val data = hashOps().entries(refreshKey(jti))
      if (data == null || data.isEmpty()) continue
      try {
        val issued = Instant.ofEpochMilli(data["issuedAt"]!!.toLong())
        val expires = Instant.ofEpochMilli(data["expiresAt"]!!.toLong())
        val meta = RefreshTokenMetadata(
          jti = data["jti"] ?: jti,
          userId = UUID.fromString(data["userId"]!!),
          issuedAt = issued,
          expiresAt = expires,
          deviceId = data["deviceId"]?.ifBlank { null },
          ip = data["ip"]?.ifBlank { null },
          userAgent = data["userAgent"]?.ifBlank { null },
          revoked = data["revoked"] == "true"
        )
        result.add(meta)
      } catch (_: Exception) { /* skip malformed */ }
    }
    return result
  }
}

data class TokenPair(
  val accessToken: String,
  val accessJti: String,
  val refreshToken: String,
  val refreshJti: String
)
