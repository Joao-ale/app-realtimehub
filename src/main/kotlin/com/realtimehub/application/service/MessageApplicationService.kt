package com.realtimehub.application.service

import com.realtimehub.domain.message.entity.Message
import com.realtimehub.domain.message.entity.MessageType
import com.realtimehub.domain.port.MessageRepository
import com.realtimehub.infrastructure.event.DomainEventPublisher
import com.realtimehub.shared.domain.DomainError
import com.realtimehub.shared.domain.Result
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageApplicationService(
    private val messageRepository: MessageRepository,
    private val domainEventPublisher: DomainEventPublisher,
) {

    @Transactional
    suspend fun sendMessage(
        chatId: String,
        senderId: String,
        content: String,
        messageType: String = "TEXT",
        replyToId: String? = null,
    ): Result<Message> {
        return try {
            val type = try {
                when (messageType.uppercase()) {
                    "TEXT" -> MessageType.TEXT
                    "IMAGE" -> MessageType.IMAGE
                    "FILE" -> MessageType.FILE
                    "AUDIO" -> MessageType.AUDIO
                    "VIDEO" -> MessageType.VIDEO
                    "EMOJI" -> MessageType.EMOJI
                    else -> MessageType.TEXT
                }
            } catch (e: Exception) {
                MessageType.text()
            }

            val message = Message.create(
                chatId = chatId,
                senderId = senderId,
                content = content,
                type = type,
                replyToId = replyToId,
            )

            val savedMessage = messageRepository.save(message)
            domainEventPublisher.publishAll(savedMessage.getDomainEvents())
            savedMessage.clearDomainEvents()

            Result.Success(savedMessage)
        } catch (e: IllegalArgumentException) {
            Result.Failure(
                DomainError.ValidationError(
                    message = e.message ?: "Invalid message data",
                ),
            )
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to send message: ${e.message}",
                ),
            )
        }
    }

    /**
     * Get message by ID.
     */
    @Transactional(readOnly = true)
    suspend fun getMessageById(messageId: String): Result<Message> {
        return try {
            val message = messageRepository.findById(messageId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Message not found: $messageId",
                    ),
                )
            Result.Success(message)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get message: ${e.message}",
                ),
            )
        }
    }

    /**
     * Get chat messages with pagination.
     */
    @Transactional(readOnly = true)
    suspend fun getChatMessages(chatId: String, limit: Int = 50, offset: Int = 0): Result<List<Message>> {
        return try {
            val messages = messageRepository.findByChatId(chatId, limit, offset)
            Result.Success(messages)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get chat messages: ${e.message}",
                ),
            )
        }
    }

    /**
     * Edit a message.
     */
    @Transactional
    suspend fun editMessage(messageId: String, newContent: String): Result<Message> {
        return try {
            val message = messageRepository.findById(messageId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Message not found: $messageId",
                    ),
                )

            val editedMessage = message.edit(newContent)
            val savedMessage = messageRepository.save(editedMessage)
            domainEventPublisher.publishAll(savedMessage.getDomainEvents())
            savedMessage.clearDomainEvents()

            Result.Success(savedMessage)
        } catch (e: IllegalArgumentException) {
            Result.Failure(
                DomainError.ValidationError(
                    message = e.message ?: "Invalid message data",
                ),
            )
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to edit message: ${e.message}",
                ),
            )
        }
    }

    /**
     * Delete a message.
     */
    @Transactional
    suspend fun deleteMessage(messageId: String): Result<Message> {
        return try {
            val message = messageRepository.findById(messageId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Message not found: $messageId",
                    ),
                )

            val deletedMessage = message.delete()
            val savedMessage = messageRepository.save(deletedMessage)
            domainEventPublisher.publishAll(savedMessage.getDomainEvents())
            savedMessage.clearDomainEvents()

            Result.Success(savedMessage)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to delete message: ${e.message}",
                ),
            )
        }
    }
}
