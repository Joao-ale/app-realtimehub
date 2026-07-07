package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * JPA Entity for Message persistence.
 * Maps to the 'messages' table in the database.
 */
@Entity
@Table(name = "messages")
data class MessageEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "chat_id", length = 36, nullable = false)
    val chatId: String = "",

    @Column(name = "sender_id", length = 36, nullable = false)
    val senderId: String = "",

    @Column(name = "content", columnDefinition = "TEXT", nullable = true)
    val content: String? = null,

    @Column(name = "message_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    val messageType: MessageTypeEnum = MessageTypeEnum.TEXT,

    @Column(name = "is_edited", nullable = false)
    val isEdited: Boolean = false,

    @Column(name = "edited_at", nullable = true)
    val editedAt: LocalDateTime? = null,

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at", nullable = true)
    val deletedAt: LocalDateTime? = null,

    @Column(name = "reply_to_id", length = 36, nullable = true)
    val replyToId: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor() : this(
        id = "",
        chatId = "",
        senderId = "",
        content = null,
        messageType = MessageTypeEnum.TEXT,
        isEdited = false,
        editedAt = null,
        isDeleted = false,
        deletedAt = null,
        replyToId = null,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
}

enum class MessageTypeEnum {
    TEXT,
    IMAGE,
    FILE,
    AUDIO,
    VIDEO,
    EMOJI,
}
