package com.realtimehub.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "messages")
class MessageEntity(
	@Id
    @Column(columnDefinition = "uuid")
    var id: UUID? = null,

	@Column(name = "chat_id", nullable = false, columnDefinition = "uuid")
    var chatId: UUID = UUID.randomUUID(),

	@Column(name = "sender_id", nullable = false, columnDefinition = "uuid")
    var senderId: UUID = UUID.randomUUID(),

	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var type: MessageTypeEntity = MessageTypeEntity.TEXT,

	@Column(columnDefinition = "TEXT")
    var content: String? = null,

	@Column(name = "media_url")
    var mediaUrl: String? = null,

	@Column(name = "reply_to_id", columnDefinition = "uuid")
    var replyToId: UUID? = null,

	@Column(name = "forwarded_from_id", columnDefinition = "uuid")
    var forwardedFromId: UUID? = null,

	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: MessageStatusEntity = MessageStatusEntity.SENT,

	@Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

	@Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

	@Column(name = "edited_at")
    var editedAt: Instant? = null,

	@Column(name = "deleted_at")
    var deletedAt: Instant? = null,

	@OneToMany(mappedBy = "messageId", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var reactions: MutableList<MessageReactionEntity> = mutableListOf(),
)

enum class MessageTypeEntity {
    TEXT,
    IMAGE,
    FILE,
    AUDIO,
    SYSTEM,
}

enum class MessageStatusEntity {
    SENT,
    DELIVERED,
    READ,
    DELETED,
}

@Entity
@Table(name = "message_reactions")
class MessageReactionEntity(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID? = null,

    @Column(name = "message_id", nullable = false, columnDefinition = "uuid")
    var messageId: UUID = UUID.randomUUID(),

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    var userId: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 32)
    var emoji: String = "",

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),
)
