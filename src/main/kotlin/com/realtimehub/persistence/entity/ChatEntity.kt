package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * JPA Entity for Chat persistence.
 * Maps to the 'chats' table in the database.
 */
@Entity
@Table(name = "chats")
data class ChatEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "name", length = 255, nullable = true)
    val name: String? = null,

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    val description: String? = null,

    @Column(name = "chat_type", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    val chatType: ChatTypeEnum = ChatTypeEnum.PRIVATE,

    @Column(name = "creator_id", length = 36, nullable = false)
    val creatorId: String = "",

    @Column(name = "group_photo_url", length = 1024, nullable = true)
    val groupPhotoUrl: String? = null,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor() : this(
        id = "",
        name = null,
        description = null,
        chatType = ChatTypeEnum.PRIVATE,
        creatorId = "",
        groupPhotoUrl = null,
        isActive = true,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
}

enum class ChatTypeEnum {
    PRIVATE,
    GROUP,
}
