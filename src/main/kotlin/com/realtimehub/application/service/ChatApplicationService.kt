package com.realtimehub.application.service

import com.realtimehub.domain.chat.entity.Chat
import com.realtimehub.domain.chat.entity.ChatMapper
import com.realtimehub.domain.port.ChatRepository
import com.realtimehub.infrastructure.event.DomainEventPublisher
import com.realtimehub.interfaces.dto.chat.ChatRequestDTO
import com.realtimehub.interfaces.dto.chat.ChatResponseDTO
import com.realtimehub.interfaces.dto.chat.ChatUpdateDTO
import com.realtimehub.shared.domain.DomainError
import com.realtimehub.shared.domain.Result
import com.realtimehub.shared.utils.IdUtils.generateId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatApplicationService(
    private val chatRepository: ChatRepository,
    private val domainEventPublisher: DomainEventPublisher,
    private val chatMapper: ChatMapper
) {
    @Transactional
    suspend fun createGroupChat(
        request: ChatRequestDTO
    ): Result<ChatResponseDTO> {
        return try {
            val chat = Chat.createGroup(
                name = request.name ?: throw IllegalArgumentException("Chat name is required for group chat"),
                description = request.description,
                creatorId = request.creatorId ?: throw IllegalArgumentException("Creator ID is required for group chat"),
                groupPhotoUrl = request.groupPhotoUrl,
            )

            val savedChat = chatRepository.save(chat)
            domainEventPublisher.publishAll(savedChat.getDomainEvents())
            savedChat.clearDomainEvents()

            Result.Success(chatMapper.toResponseDTO(savedChat))
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

    @Transactional
    suspend fun createPrivateChat(request: ChatRequestDTO): Result<ChatResponseDTO> {
        return try {
            val creatorId = request.creatorId ?: throw IllegalArgumentException("Creator ID is required for private chat")
            val chat = Chat.createPrivate(creatorId = creatorId)
            val savedChat = chatRepository.save(chat)
            domainEventPublisher.publishAll(savedChat.getDomainEvents())
            savedChat.clearDomainEvents()

            Result.Success(chatMapper.toResponseDTO(savedChat))
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to create private chat: ${e.message}",
                ),
            )
        }
    }

    @Transactional(readOnly = true)
    suspend fun getChatById(chatId: String): Result<ChatResponseDTO> {
        return try {
            val chat = chatRepository.findById(chatId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Chat not found: $chatId",
                    ),
                )
            Result.Success(chatMapper.toResponseDTO(chat))
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get chat: ${e.message}",
                ),
            )
        }
    }

    @Transactional(readOnly = true)
    suspend fun getUserChats(userId: String): Result<List<ChatResponseDTO>> {
        return try {
            val chats = chatRepository.findByUserId(userId)
                .map { chatMapper.toResponseDTO(it) }
            Result.Success(chats)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get user chats: ${e.message}",
                ),
            )
        }
    }

    @Transactional
    suspend fun updateGroupChat(
         request: ChatUpdateDTO,
         chatId: String
        ): Result<ChatResponseDTO> {
        return try {
            val chat = chatRepository.findById(chatId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Chat not found: $chatId",
                    ),
                )

            val updatedChat = chat.updateGroupInfo(
                name = request.name,
                description = request.description,
                groupPhotoUrl = request.groupPhotoUrl,
            )

            val savedChat = chatRepository.save(updatedChat)
            domainEventPublisher.publishAll(savedChat.getDomainEvents())
            savedChat.clearDomainEvents()

            Result.Success(chatMapper.toResponseDTO(savedChat))
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
