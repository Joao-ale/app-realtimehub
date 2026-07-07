package com.realtimehub.application.service

import com.realtimehub.domain.chat.entity.Chat
import com.realtimehub.domain.chat.repository.ChatRepository
import com.realtimehub.infrastructure.event.DomainEventPublisher
import com.realtimehub.shared.domain.DomainError
import com.realtimehub.shared.domain.Result
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Application service for chat operations.
 * Orchestrates domain logic and coordinates with repositories.
 */
@Service
class ChatApplicationService(
    private val chatRepository: ChatRepository,
    private val domainEventPublisher: DomainEventPublisher,
) {

    /**
     * Create a new group chat.
     */
    @Transactional
    suspend fun createGroupChat(
        name: String,
        description: String? = null,
        creatorId: String,
        groupPhotoUrl: String? = null,
    ): Result<Chat> {
        return try {
            val chat = Chat.createGroup(
                name = name,
                description = description,
                creatorId = creatorId,
                groupPhotoUrl = groupPhotoUrl,
            )

            val savedChat = chatRepository.save(chat)
            domainEventPublisher.publishAll(savedChat.getDomainEvents())
            savedChat.clearDomainEvents()

            Result.Success(savedChat)
        } catch (e: IllegalArgumentException) {
            Result.Failure(
                DomainError.ValidationError(
                    message = e.message ?: "Invalid chat data",
                ),
            )
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to create chat: ${e.message}",
                ),
            )
        }
    }

    /**
     * Create a new private chat.
     */
    @Transactional
    suspend fun createPrivateChat(creatorId: String): Result<Chat> {
        return try {
            val chat = Chat.createPrivate(creatorId = creatorId)
            val savedChat = chatRepository.save(chat)
            domainEventPublisher.publishAll(savedChat.getDomainEvents())
            savedChat.clearDomainEvents()

            Result.Success(savedChat)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to create private chat: ${e.message}",
                ),
            )
        }
    }

    /**
     * Get chat by ID.
     */
    @Transactional(readOnly = true)
    suspend fun getChatById(chatId: String): Result<Chat> {
        return try {
            val chat = chatRepository.findById(chatId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Chat not found: $chatId",
                    ),
                )
            Result.Success(chat)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get chat: ${e.message}",
                ),
            )
        }
    }

    /**
     * Get all chats created by user.
     */
    @Transactional(readOnly = true)
    suspend fun getUserChats(userId: String): Result<List<Chat>> {
        return try {
            val chats = chatRepository.findAllByCreatorId(userId)
            Result.Success(chats)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get user chats: ${e.message}",
                ),
            )
        }
    }

    /**
     * Update group chat info.
     */
    @Transactional
    suspend fun updateGroupChat(
        chatId: String,
        name: String? = null,
        description: String? = null,
        groupPhotoUrl: String? = null,
    ): Result<Chat> {
        return try {
            val chat = chatRepository.findById(chatId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Chat not found: $chatId",
                    ),
                )

            val updatedChat = chat.updateGroupInfo(
                name = name,
                description = description,
                groupPhotoUrl = groupPhotoUrl,
            )

            val savedChat = chatRepository.save(updatedChat)
            domainEventPublisher.publishAll(savedChat.getDomainEvents())
            savedChat.clearDomainEvents()

            Result.Success(savedChat)
        } catch (e: IllegalArgumentException) {
            Result.Failure(
                DomainError.ValidationError(
                    message = e.message ?: "Invalid chat data",
                ),
            )
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to update chat: ${e.message}",
                ),
            )
        }
    }
}
