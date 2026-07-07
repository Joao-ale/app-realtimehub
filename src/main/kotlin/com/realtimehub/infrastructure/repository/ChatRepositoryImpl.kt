package com.realtimehub.infrastructure.repository

import com.realtimehub.domain.chat.entity.Chat
import com.realtimehub.domain.chat.repository.ChatRepository
import com.realtimehub.persistence.converter.ChatConverter
import com.realtimehub.persistence.repository.ChatJpaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 * Implementation of ChatRepository domain interface.
 * Bridges domain logic with JPA persistence layer.
 */
@Component
class ChatRepositoryImpl(
    private val chatJpaRepository: ChatJpaRepository,
    private val chatConverter: ChatConverter,
) : ChatRepository {

    override suspend fun save(aggregate: Chat): Chat = withContext(Dispatchers.IO) {
        val entity = chatConverter.toEntity(aggregate)
        val savedEntity = chatJpaRepository.save(entity)
        chatConverter.toDomain(savedEntity)
    }

    override suspend fun findById(id: String): Chat? = withContext(Dispatchers.IO) {
        val entity = chatJpaRepository.findById(id).orElse(null) ?: return@withContext null
        chatConverter.toDomain(entity)
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext if (chatJpaRepository.existsById(id)) {
            chatJpaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    override suspend fun findByIdAndIsActive(id: String, isActive: Boolean): Chat? =
        withContext(Dispatchers.IO) {
            if (!isActive) return@withContext findById(id)
            val entity = chatJpaRepository.findByIdAndIsActiveTrue(id) ?: return@withContext null
            chatConverter.toDomain(entity)
        }

    override suspend fun findAllByCreatorId(creatorId: String): List<Chat> =
        withContext(Dispatchers.IO) {
            chatJpaRepository.findAllByCreatorIdAndIsActiveTrue(creatorId)
                .map { chatConverter.toDomain(it) }
        }
}
