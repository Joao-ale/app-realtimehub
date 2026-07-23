package com.realtimehub.domain.port

import com.realtimehub.domain.chat.entity.Chat

interface ChatRepository {
    suspend fun save(chat: Chat): Chat
    suspend fun findById(id: String): Chat?
    suspend fun delete(id: String): Boolean
    suspend fun findByUserId(userId: String): List<Chat>
}
