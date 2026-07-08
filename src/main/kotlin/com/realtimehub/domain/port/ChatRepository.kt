package com.realtimehub.domain.port


import com.realtimehub.domain.chat.entity.Chat

interface ChatRepository {
    fun save(chat: Chat): Chat
    fun findById(id: String): Chat?
    fun findByUserId(userId: String): List<Chat>

    fun findPrivateChatBetween(userId1: String, userId2: String): Chat?
}
