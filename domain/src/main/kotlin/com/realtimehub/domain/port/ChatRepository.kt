package com.realtimehub.domain.port

import com.realtimehub.domain.model.Chat
import com.realtimehub.domain.model.ChatType
import java.util.UUID

interface ChatRepository {
    fun save(chat: Chat): Chat
    fun findById(id: UUID): Chat?
    fun findByUserId(userId: UUID): List<Chat>
    fun findPrivateChatBetween(userId1: UUID, userId2: UUID): Chat?
}
