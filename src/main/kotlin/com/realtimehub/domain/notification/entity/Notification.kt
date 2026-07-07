package com.realtimehub.domain.notification.entity

import com.realtimehub.domain.notification.valueobject.NotificationType
import com.realtimehub.shared.domain.AggregateRoot
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

/**
 * Notification aggregate root.
 * Represents a notification sent to a user.
 * Uses LocalDateTime with Brasilia timezone (UTC-3).
 */
class Notification(
    override val id: String,
    val userId: String,
    val title: String,
    val description: String?,
    val type: NotificationType,
    val relatedUserId: String?,
    val relatedMessageId: String?,
    val relatedChatId: String?,
    val isRead: Boolean,
    val readAt: LocalDateTime?,
    val createdAt: LocalDateTime,
) : AggregateRoot<String>(id) {

    companion object {
        private val BRASIL_ZONE = ZoneId.of("America/Sao_Paulo")

        /**
         * Factory method to create a new notification.
         */
        fun create(
            userId: String,
            title: String,
            description: String? = null,
            type: NotificationType,
            relatedUserId: String? = null,
            relatedMessageId: String? = null,
            relatedChatId: String? = null,
        ): Notification {
            require(title.isNotBlank()) { "Notification title cannot be blank" }

            return Notification(
                id = UUID.randomUUID().toString(),
                userId = userId,
                title = title,
                description = description,
                type = type,
                relatedUserId = relatedUserId,
                relatedMessageId = relatedMessageId,
                relatedChatId = relatedChatId,
                isRead = false,
                readAt = null,
                createdAt = LocalDateTime.now(BRASIL_ZONE),
            )
        }
    }

    /**
     * Mark notification as read.
     */
    fun markAsRead(): Notification {
        return Notification(
            id = this.id,
            userId = this.userId,
            title = this.title,
            description = this.description,
            type = this.type,
            relatedUserId = this.relatedUserId,
            relatedMessageId = this.relatedMessageId,
            relatedChatId = this.relatedChatId,
            isRead = true,
            readAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
            createdAt = this.createdAt,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Notification) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
