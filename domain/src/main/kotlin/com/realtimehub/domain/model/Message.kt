package com.realtimehub.domain.model

import java.time.Instant
import java.util.UUID

data class MessageReaction(
    val id: UUID,
    val messageId: UUID,
    val userId: UUID,
    val emoji: String,
    val createdAt: Instant,
)

data class Message(
    override val id: UUID,
    val chatId: UUID,
    val senderId: UUID,
    val type: MessageType,
    val content: String?,
    val mediaUrl: String?,
    val replyToId: UUID?,
    val forwardedFromId: UUID?,
    val status: MessageStatus,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    val editedAt: Instant?,
    val deletedAt: Instant?,
    val reactions: List<MessageReaction> = emptyList(),
) : AggregateRoot(id, createdAt, updatedAt) {
    fun edit(content: String, updatedAt: Instant): Message =
        copy(content = content, editedAt = updatedAt, updatedAt = updatedAt)

    fun markDeleted(updatedAt: Instant): Message =
        copy(status = MessageStatus.DELETED, deletedAt = updatedAt, updatedAt = updatedAt)

    fun withStatus(status: MessageStatus, updatedAt: Instant): Message =
        copy(status = status, updatedAt = updatedAt)
}
