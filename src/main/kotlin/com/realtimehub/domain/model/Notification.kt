package com.realtimehub.domain.model

import java.time.LocalDateTime

data class Notification(
    override val id: String,
    val userId: String,
    val type: NotificationType,
    val title: String,
    val body: String,
    val referenceId: String?,
    val status: NotificationStatus,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    val readAt: LocalDateTime?,
) : AggregateRoot(id, createdAt, updatedAt) {
    fun markRead(readAt: LocalDateTime): Notification =
        copy(status = NotificationStatus.READ, readAt = readAt, updatedAt = readAt)
}
