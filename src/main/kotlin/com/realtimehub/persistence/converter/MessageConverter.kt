package com.realtimehub.persistence.converter

import com.realtimehub.domain.message.entity.Message
import com.realtimehub.domain.message.valueobject.MessageType
import com.realtimehub.persistence.entity.MessageEntity
import com.realtimehub.persistence.entity.MessageTypeEnum
import org.springframework.stereotype.Component

/**
 * Converter between Message domain entity and MessageEntity JPA entity.
 * Handles bidirectional conversion between domain and persistence models.
 */
@Component
class MessageConverter {

    /**
     * Convert MessageEntity (JPA) to Message (Domain).
     */
    fun toDomain(entity: MessageEntity): Message {
        return Message(
            id = entity.id,
            chatId = entity.chatId,
            senderId = entity.senderId,
            content = entity.content,
            type = when (entity.messageType) {
                MessageTypeEnum.TEXT -> MessageType.text()
                MessageTypeEnum.IMAGE -> MessageType.image()
                MessageTypeEnum.FILE -> MessageType.file()
                MessageTypeEnum.AUDIO -> MessageType.audio()
                MessageTypeEnum.VIDEO -> MessageType.video()
                MessageTypeEnum.EMOJI -> MessageType.emoji()
            },
            isEdited = entity.isEdited,
            editedAt = entity.editedAt,
            isDeleted = entity.isDeleted,
            deletedAt = entity.deletedAt,
            replyToId = entity.replyToId,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }

    /**
     * Convert Message (Domain) to MessageEntity (JPA).
     */
    fun toEntity(domain: Message): MessageEntity {
        return MessageEntity(
            id = domain.id,
            chatId = domain.chatId,
            senderId = domain.senderId,
            content = domain.content,
            messageType = when (domain.type.value) {
                MessageType.Type.TEXT -> MessageTypeEnum.TEXT
                MessageType.Type.IMAGE -> MessageTypeEnum.IMAGE
                MessageType.Type.FILE -> MessageTypeEnum.FILE
                MessageType.Type.AUDIO -> MessageTypeEnum.AUDIO
                MessageType.Type.VIDEO -> MessageTypeEnum.VIDEO
                MessageType.Type.EMOJI -> MessageTypeEnum.EMOJI
            },
            isEdited = domain.isEdited,
            editedAt = domain.editedAt,
            isDeleted = domain.isDeleted,
            deletedAt = domain.deletedAt,
            replyToId = domain.replyToId,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )
    }
}
