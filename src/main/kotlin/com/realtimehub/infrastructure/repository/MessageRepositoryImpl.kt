package com.realtimehub.infrastructure.repository

import com.realtimehub.domain.message.entity.Message
import com.realtimehub.domain.message.repository.MessageRepository
import com.realtimehub.persistence.converter.MessageConverter
import com.realtimehub.persistence.repository.MessageJpaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

/**
 * Implementation of MessageRepository domain interface.
 * Bridges domain logic with JPA persistence layer.
 */
@Component
class MessageRepositoryImpl(
    private val messageJpaRepository: MessageJpaRepository,
    private val messageConverter: MessageConverter,
) : MessageRepository {

    override suspend fun save(aggregate: Message): Message = withContext(Dispatchers.IO) {
        val entity = messageConverter.toEntity(aggregate)
        val savedEntity = messageJpaRepository.save(entity)
        messageConverter.toDomain(savedEntity)
    }

    override suspend fun findById(id: String): Message? = withContext(Dispatchers.IO) {
        val entity = messageJpaRepository.findById(id).orElse(null) ?: return@withContext null
        messageConverter.toDomain(entity)
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext if (messageJpaRepository.existsById(id)) {
            messageJpaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    override suspend fun findByChatId(chatId: String, limit: Int, offset: Int): List<Message> =
        withContext(Dispatchers.IO) {
            val pageable = PageRequest.of(
                offset / limit,
                limit,
                Sort.by(Sort.Direction.DESC, "createdAt"),
            )
            messageJpaRepository.findByChatIdAndIsDeletedFalse(chatId, pageable)
                .map { messageConverter.toDomain(it) }
        }

    override suspend fun findBySenderId(senderId: String): List<Message> =
        withContext(Dispatchers.IO) {
            messageJpaRepository.findBySenderIdAndIsDeletedFalse(senderId)
                .map { messageConverter.toDomain(it) }
        }

    override suspend fun findByReplyToId(replyToId: String): List<Message> =
        withContext(Dispatchers.IO) {
            messageJpaRepository.findByReplyToIdAndIsDeletedFalse(replyToId)
                .map { messageConverter.toDomain(it) }
        }
}
