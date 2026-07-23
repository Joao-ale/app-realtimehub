package com.realtimehub.domain.port

import com.realtimehub.domain.message.entity.Message

interface MessageRepository {
    suspend fun save(message: Message): Message
    suspend fun findById(id: String): Message?
    suspend fun delete(id: String): Boolean
    suspend fun findByChatId(chatId: String, limit: Int, offset: Int): List<Message>
    suspend fun findBySenderId(senderId: String): List<Message>
    suspend fun findByReplyToId(replyToId: String): List<Message>
}
