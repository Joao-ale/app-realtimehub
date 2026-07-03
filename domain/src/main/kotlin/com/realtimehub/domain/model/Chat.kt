package com.realtimehub.domain.model

import java.time.Instant
import java.util.UUID

data class Chat(
    override val id: UUID,
    val type: ChatType,
    val name: String?,
    val createdBy: UUID,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : AggregateRoot(id, createdAt, updatedAt) {
    fun withName(name: String, updatedAt: Instant): Chat =
        copy(name = name, updatedAt = updatedAt)
}
