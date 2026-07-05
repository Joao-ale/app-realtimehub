package com.realtimehub.domain.port

import com.realtimehub.domain.model.Message
import java.util.UUID

interface MessageRepository {
    fun save(message: Message): Message
    fun findById(id: UUID): Message?
    fun findByChatId(chatId: UUID, pageRequest: PageRequest): PageResult<Message>
}
