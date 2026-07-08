package com.realtimehub.domain.message.entity

import com.realtimehub.domain.message.event.MessageCreatedEvent
import com.realtimehub.domain.message.event.MessageDeletedEvent
import com.realtimehub.domain.message.event.MessageEditedEvent
import com.realtimehub.shared.domain.AggregateRoot
import com.realtimehub.shared.utils.DateTimeUtils
import java.time.LocalDateTime
import java.util.UUID


class Message(
    override val id: String,
    val chatId: String,
    val senderId: String,
    val content: String?,
    val type: MessageType,
    val isEdited: Boolean,
    val editedAt: LocalDateTime?,
    val isDeleted: Boolean,
    val deletedAt: LocalDateTime?,
    val replyToId: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) : AggregateRoot<String>(id) {

    companion object {

        fun create(
            chatId: String,
            senderId: String,
            content: String,
            type: MessageType = MessageType.TEXT,
            replyToId: String? = null,
        ): Message {
            require(content.isNotBlank()) { "Message content cannot be blank" }
            require(content.length <= 4000) { "Message content cannot exceed 4000 characters" }

            val now = DateTimeUtils.now()
            val messageId = UUID.randomUUID().toString()

            val message = Message(
                id = messageId,
                chatId = chatId,
                senderId = senderId,
                content = content,
                type = type,
                isEdited = false,
                editedAt = null,
                isDeleted = false,
                deletedAt = null,
                replyToId = replyToId,
                createdAt = now,
                updatedAt = now,
            )

            message.registerEvent(
                MessageCreatedEvent(
                    aggregateId = messageId,
                    chatId = chatId,
                    senderId = senderId,
                    content = content,
                    type = type.name,
                    replyToId = replyToId,
                )
            )

            return message
        }
    }

    fun edit(newContent: String): Message {
        require(!isDeleted) { "Cannot edit a deleted message" }
        require(newContent.isNotBlank()) { "Message content cannot be blank" }
        require(newContent.length <= 4000) { "Message content cannot exceed 4000 characters" }
        require(newContent != content) { "New content must be different from current content" }

        val now = DateTimeUtils.now()
        val updated = Message(
            id = this.id,
            chatId = this.chatId,
            senderId = this.senderId,
            content = newContent,
            type = this.type,
            isEdited = true,
            editedAt = now,
            isDeleted = this.isDeleted,
            deletedAt = this.deletedAt,
            replyToId = this.replyToId,
            createdAt = this.createdAt,
            updatedAt = now,
        )

        updated.registerEvent(
            MessageEditedEvent(
                aggregateId = this.id,
                chatId = this.chatId,
                senderId = this.senderId,
                previousContent = this.content,
                newContent = newContent,
            )
        )

        return updated
    }

    fun delete(): Message {
        require(!isDeleted) { "Message is already deleted" }

        val now = DateTimeUtils.now()
        val updated = Message(
            id = this.id,
            chatId = this.chatId,
            senderId = this.senderId,
            content = null,
            type = this.type,
            isEdited = this.isEdited,
            editedAt = this.editedAt,
            isDeleted = true,
            deletedAt = now,
            replyToId = this.replyToId,
            createdAt = this.createdAt,
            updatedAt = now,
        )

        updated.registerEvent(
            MessageDeletedEvent(
                aggregateId = this.id,
                chatId = this.chatId,
                senderId = this.senderId,
            )
        )

        return updated
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Message) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
