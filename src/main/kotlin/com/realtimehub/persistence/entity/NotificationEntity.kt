package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * JPA Entity for Notification persistence.
 * Maps to the 'notifications' table in the database.
 */
@Entity
@Table(name = "notifications")
data class NotificationEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "user_id", length = 36, nullable = false)
    val userId: String = "",

    @Column(name = "title", length = 255, nullable = false)
    val title: String = "",

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    val description: String? = null,

    @Column(name = "notification_type", length = 50, nullable = false)
    val notificationType: String = "",

    @Column(name = "related_user_id", length = 36, nullable = true)
    val relatedUserId: String? = null,

    @Column(name = "related_message_id", length = 36, nullable = true)
    val relatedMessageId: String? = null,

    @Column(name = "related_chat_id", length = 36, nullable = true)
    val relatedChatId: String? = null,

    @Column(name = "is_read", nullable = false)
    val isRead: Boolean = false,

    @Column(name = "read_at", nullable = true)
    val readAt: LocalDateTime? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor() : this(
        id = "",
        userId = "",
        title = "",
        description = null,
        notificationType = "",
        relatedUserId = null,
        relatedMessageId = null,
        relatedChatId = null,
        isRead = false,
        readAt = null,
        createdAt = LocalDateTime.now(),
    )
}
