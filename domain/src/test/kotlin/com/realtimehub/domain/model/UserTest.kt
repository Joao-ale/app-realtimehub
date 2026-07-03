package com.realtimehub.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class UserTest {
    private val now = Instant.parse("2026-07-02T12:00:00Z")
    private val userId = UUID.randomUUID()

    private fun sampleUser() =
        User(
            id = userId,
            name = "John Doe",
            email = "john@example.com",
            passwordHash = "hashed",
            photoUrl = null,
            status = UserStatus.OFFLINE,
            lastSeenAt = null,
            createdAt = now,
            updatedAt = now,
        )

    @Test
    fun `withProfile should update name and photo`() {
        val updated = sampleUser().withProfile("Jane Doe", "https://photo.url/img.png", now.plusSeconds(60))

        assertEquals("Jane Doe", updated.name)
        assertEquals("https://photo.url/img.png", updated.photoUrl)
        assertEquals(now.plusSeconds(60), updated.updatedAt)
    }

    @Test
    fun `withStatus should update online status and last seen`() {
        val lastSeen = now.plusSeconds(120)
        val updated = sampleUser().withStatus(UserStatus.ONLINE, lastSeen, lastSeen)

        assertEquals(UserStatus.ONLINE, updated.status)
        assertEquals(lastSeen, updated.lastSeenAt)
    }
}
