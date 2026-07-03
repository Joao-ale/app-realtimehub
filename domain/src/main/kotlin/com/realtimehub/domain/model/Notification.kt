package com.realtimehub.domain.model

import java.time.Instant
import java.util.UUID

data class Notification(
    override val id: UUID,
    val userId: UUID,
    val type: NotificationType,
    val title: String,
    val body: String,
    val referenceId: UUID?,
    val status: NotificationStatus,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    val readAt: Instant?,
) : AggregateRoot(id, createdAt, updatedAt) {
    fun markRead(readAt: Instant): Notification =
        copy(status = NotificationStatus.READ, readAt = readAt, updatedAt = readAt)
}
