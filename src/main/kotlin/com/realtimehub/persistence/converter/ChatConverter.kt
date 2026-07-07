package com.realtimehub.persistence.converter

import com.realtimehub.domain.chat.entity.Chat
import com.realtimehub.domain.chat.valueobject.ChatType
import com.realtimehub.persistence.entity.ChatEntity
import com.realtimehub.persistence.entity.ChatTypeEnum
import org.springframework.stereotype.Component

/**
 * Converter between Chat domain entity and ChatEntity JPA entity.
 * Handles bidirectional conversion between domain and persistence models.
 */
@Component
class ChatConverter {

    /**
     * Convert ChatEntity (JPA) to Chat (Domain).
     */
    fun toDomain(entity: ChatEntity): Chat {
        return Chat(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            type = when (entity.chatType) {
                ChatTypeEnum.PRIVATE -> ChatType.private()
                ChatTypeEnum.GROUP -> ChatType.group()
            },
            creatorId = entity.creatorId,
            groupPhotoUrl = entity.groupPhotoUrl,
            isActive = entity.isActive,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }

    /**
     * Convert Chat (Domain) to ChatEntity (JPA).
     */
    fun toEntity(domain: Chat): ChatEntity {
        return ChatEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            chatType = when {
                domain.type.isPrivate() -> ChatTypeEnum.PRIVATE
                domain.type.isGroup() -> ChatTypeEnum.GROUP
                else -> ChatTypeEnum.PRIVATE
            },
            creatorId = domain.creatorId,
            groupPhotoUrl = domain.groupPhotoUrl,
            isActive = domain.isActive,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )
    }
}
