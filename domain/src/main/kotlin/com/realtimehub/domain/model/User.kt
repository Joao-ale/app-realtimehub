package com.realtimehub.domain.model

import java.time.Instant
import java.util.UUID

data class User(
    override val id: UUID,
    val name: String,
    val email: String,
    val passwordHash: String,
    val photoUrl: String?,
    val status: UserStatus,
    val lastSeenAt: Instant?,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : AggregateRoot(id, createdAt, updatedAt) {
    fun withProfile(name: String, photoUrl: String?, updatedAt: Instant): User =
        copy(name = name, photoUrl = photoUrl, updatedAt = updatedAt)

    fun withPassword(passwordHash: String, updatedAt: Instant): User =
        copy(passwordHash = passwordHash, updatedAt = updatedAt)

    fun withStatus(status: UserStatus, lastSeenAt: Instant?, updatedAt: Instant): User =
        copy(status = status, lastSeenAt = lastSeenAt, updatedAt = updatedAt)
}
