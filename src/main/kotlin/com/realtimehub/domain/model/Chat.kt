package com.realtimehub.domain.model

import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

data class Chat(
    override val id: String,
    val type: ChatType,
    val name: String?,
    val createdBy: UUID,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
) : AggregateRoot(id, createdAt, updatedAt) {
    fun withName(name: String, updatedAt: LocalDateTime): Chat =
        copy(name = name, updatedAt = updatedAt)
}
