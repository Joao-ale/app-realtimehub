package com.realtimehub.domain.chat.entity

import com.realtimehub.domain.chat.event.ChatCreatedEvent
import com.realtimehub.interfaces.dto.chat.ChatResponseDTO
import com.realtimehub.shared.domain.AggregateRoot
import com.realtimehub.shared.utils.DateTimeUtils
import java.time.LocalDateTime
import java.util.UUID

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

        fun createPrivate(creatorId: String): Chat {
            val now = DateTimeUtils.now()
            val chatId = UUID.randomUUID().toString()

            val chat = Chat(
                id = chatId,
                name = null,
                description = null,
                type = ChatType.PRIVATE,
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
                    type = ChatType.PRIVATE.toString(),
                    creatorId = creatorId,
                )
            )

            return chat
        }

        fun createGroup(
            name: String,
            description: String? = null,
            creatorId: String,
            groupPhotoUrl: String? = null,
        ): Chat {
            require(name.isNotBlank()) { "Chat name cannot be blank" }
            require(name.length <= 255) { "Chat name cannot exceed 255 characters" }

            val now = DateTimeUtils.now()
            val chatId = UUID.randomUUID().toString()

            val chat = Chat(
                id = chatId,
                name = name,
                description = description,
                type = ChatType.GROUP,
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
                    type = ChatType.GROUP.toString(),
                    creatorId = creatorId,
                )
            )

            return chat
        }
    }

    fun updateGroupInfo(
        name: String? = null,
        description: String? = null,
        groupPhotoUrl: String? = null,
    ): Chat {
        require(type == ChatType.GROUP) { "Can only update group chats" }
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
            updatedAt = DateTimeUtils.now(),
        )
    }

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
            updatedAt = DateTimeUtils.now(),
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chat) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

}
