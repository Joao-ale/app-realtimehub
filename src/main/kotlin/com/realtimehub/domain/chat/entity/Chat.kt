package com.realtimehub.domain.chat.entity

import com.realtimehub.domain.chat.event.ChatCreatedEvent
import com.realtimehub.domain.chat.valueobject.ChatId
import com.realtimehub.domain.chat.valueobject.ChatType
import com.realtimehub.domain.user.valueobject.UserId
import com.realtimehub.shared.domain.AggregateRoot
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

/**
 * Chat aggregate root.
 * Represents a conversation between users (private or group).
 * Uses LocalDateTime with Brasilia timezone (UTC-3).
 */
class Chat(
    override val id: String,
    val name: String?,
    val description: String?,
    val type: ChatType,
    val creatorId: String,
    val groupPhotoUrl: String?,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) : AggregateRoot<String>(id) {

    companion object {
        private val BRASIL_ZONE = ZoneId.of("America/Sao_Paulo")

        /**
         * Factory method to create a new private chat.
         */
        fun createPrivate(creatorId: String): Chat {
            val now = LocalDateTime.now(BRASIL_ZONE)
            val chatId = UUID.randomUUID().toString()

            val chat = Chat(
                id = chatId,
                name = null,
                description = null,
                type = ChatType.private(),
                creatorId = creatorId,
                groupPhotoUrl = null,
                isActive = true,
                createdAt = now,
                updatedAt = now,
            )

            chat.registerEvent(
                ChatCreatedEvent(
                    aggregateId = chatId,
                    name = null,
                    type = "PRIVATE",
                    creatorId = creatorId,
                )
            )

            return chat
        }

        /**
         * Factory method to create a new group chat.
         */
        fun createGroup(
            name: String,
            description: String? = null,
            creatorId: String,
            groupPhotoUrl: String? = null,
        ): Chat {
            require(name.isNotBlank()) { "Chat name cannot be blank" }
            require(name.length <= 255) { "Chat name cannot exceed 255 characters" }

            val now = LocalDateTime.now(BRASIL_ZONE)
            val chatId = UUID.randomUUID().toString()

            val chat = Chat(
                id = chatId,
                name = name,
                description = description,
                type = ChatType.group(),
                creatorId = creatorId,
                groupPhotoUrl = groupPhotoUrl,
                isActive = true,
                createdAt = now,
                updatedAt = now,
            )

            chat.registerEvent(
                ChatCreatedEvent(
                    aggregateId = chatId,
                    name = name,
                    type = "GROUP",
                    creatorId = creatorId,
                )
            )

            return chat
        }
    }

    /**
     * Update group chat info.
     */
    fun updateGroupInfo(
        name: String? = null,
        description: String? = null,
        groupPhotoUrl: String? = null,
    ): Chat {
        require(type.isGroup()) { "Can only update group chats" }
        if (name != null) {
            require(name.isNotBlank()) { "Chat name cannot be blank" }
            require(name.length <= 255) { "Chat name cannot exceed 255 characters" }
        }

        return Chat(
            id = this.id,
            name = name ?: this.name,
            description = description ?: this.description,
            type = this.type,
            creatorId = this.creatorId,
            groupPhotoUrl = groupPhotoUrl ?: this.groupPhotoUrl,
            isActive = this.isActive,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now(BRASIL_ZONE),
        )
    }

    /**
     * Deactivate chat.
     */
    fun deactivate(): Chat {
        return Chat(
            id = this.id,
            name = this.name,
            description = this.description,
            type = this.type,
            creatorId = this.creatorId,
            groupPhotoUrl = this.groupPhotoUrl,
            isActive = false,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now(BRASIL_ZONE),
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chat) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
