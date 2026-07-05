package com.realtimehub.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class User(
    override val id: String,
    val name: String,
    val email: String,
    val passwordHash: String,
    val status: UserStatus,
    val lastSeenAt: LocalDateTime?,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
) : AggregateRoot(id, createdAt, updatedAt) {
    fun withProfile(name: String, updatedAt: LocalDateTime): User =
        copy(name = name, updatedAt = updatedAt)

    fun withPassword(passwordHash: String, updatedAt: LocalDateTime): User =
        copy(passwordHash = passwordHash, updatedAt = updatedAt)

    fun withStatus(status: UserStatus, lastSeenAt: LocalDateTime?, updatedAt: LocalDateTime): User =
        copy(status = status, lastSeenAt = lastSeenAt, updatedAt = updatedAt)
}
