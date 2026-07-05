package com.realtimehub.domain.model

import java.time.LocalDateTime

data class MessageReaction(
    val id: String,
    val messageId: String,
    val userId: String,
    val emoji: String,
    val createdAt: LocalDateTime,
)

data class Message(
    override val id: String,
    val chatId: String,
    val senderId: String,
    val type: MessageType,
    val content: String?,
    val mediaUrl: String?,
    val replyToId: String?,
    val forwardedFromId: String?,
    val status: MessageStatus,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    val editedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?,
    val reactions: List<MessageReaction> = emptyList(),
) : AggregateRoot(id, createdAt, updatedAt) {
    fun edit(content: String, updatedAt: LocalDateTime): Message =
        copy(content = content, editedAt = updatedAt, updatedAt = updatedAt)

    fun markDeleted(updatedAt: LocalDateTime): Message =
        copy(status = MessageStatus.DELETED, deletedAt = updatedAt, updatedAt = updatedAt)

    fun withStatus(status: MessageStatus, updatedAt: LocalDateTime): Message =
        copy(status = status, updatedAt = updatedAt)
}
