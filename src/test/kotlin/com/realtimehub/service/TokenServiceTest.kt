package com.realtimehub.service

import com.realtimehub.security.JwtUtil
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.SetOperations
import java.util.*

class TokenServiceTest {

  @Test
  fun `create tokens stores refresh metadata in redis`() {
    val jwtUtil = JwtUtil("change-me-not-secure-which-needs-to-be-long-enough", 15, 7)
    val redis = mockk<StringRedisTemplate>(relaxed = true)
    val hashOps = mockk<HashOperations<String, String, String>>(relaxed = true)
    val setOps = mockk<SetOperations<String, String>>(relaxed = true)

    every { redis.opsForHash<String, String, String>() } returns hashOps
    every { redis.opsForSet<String, String>() } returns setOps

    val tokenService = TokenService(jwtUtil, redis)

    val userId = UUID.randomUUID()
    val tokens = tokenService.createTokens(userId, "device-x", "127.0.0.1", "unit-test-agent")

    assertNotNull(tokens.accessToken)
    assertNotNull(tokens.refreshToken)

    verify { hashOps.putAll(match { it.startsWith("refresh:") }, match { it["userId"] == userId.toString() }) }
    verify { setOps.add(match { it.startsWith("user_refreshs:") }, tokens.refreshJti) }
  }
}
