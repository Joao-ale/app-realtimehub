package com.realtimehub.interfaces.controller

import com.realtimehub.domain.message.entity.Message
import com.realtimehub.interfaces.dto.message.MessageEditDTO
import com.realtimehub.interfaces.dto.message.MessageRequestDTO
import com.realtimehub.interfaces.dto.message.MessageResponseDTO
import com.realtimehub.application.service.
import com.realtimehub.shared.domain.Result
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/messages")
@Tag(name = "Messages", description = "Message management endpoints")
class MessageController(
    private val messageApplicationService: MessgeApplicationService,
) {

    /**
     * Send a new message.
     */
    @PostMapping
    @Operation(summary = "Send a message", description = "Send a new message to a chat")
    suspend fun sendMessage(
        @RequestParam chatId: String,
        @RequestBody request: MessageRequestDTO,
    ): ResponseEntity<Any> {
        val result = messageApplicationService.sendMessage(
            chatId = chatId,
            senderId = "", // TODO: Get from authentication context
            content = request.content,
            messageType = request.messageType,
            replyToId = request.replyToId,
        )

        return when (result) {
            is Result.Success -> ResponseEntity.status(HttpStatus.CREATED)
                .body(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Get message by ID.
     */
    @GetMapping("/{messageId}")
    @Operation(summary = "Get message by ID", description = "Retrieve message information by message ID")
    suspend fun getMessageById(
        @PathVariable messageId: String,
    ): ResponseEntity<Any> {
        val result = messageApplicationService.getMessageById(messageId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Get chat messages.
     */
    @GetMapping("/chat/{chatId}")
    @Operation(summary = "Get chat messages", description = "Get messages from a specific chat with pagination")
    suspend fun getChatMessages(
        @PathVariable chatId: String,
        @RequestParam(defaultValue = "50") limit: Int,
        @RequestParam(defaultValue = "0") offset: Int,
    ): ResponseEntity<Any> {
        val result = messageApplicationService.getChatMessages(chatId, limit, offset)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.map { it.toResponseDTO() })
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Edit a message.
     */
    @PutMapping("/{messageId}")
    @Operation(summary = "Edit a message", description = "Update message content")
    suspend fun editMessage(
        @PathVariable messageId: String,
        @RequestBody request: MessageEditDTO,
    ): ResponseEntity<Any> {
        val result = messageApplicationService.editMessage(messageId, request.content)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Delete a message.
     */
    @DeleteMapping("/{messageId}")
    @Operation(summary = "Delete a message", description = "Soft delete a message")
    suspend fun deleteMessage(
        @PathVariable messageId: String,
    ): ResponseEntity<Any> {
        val result = messageApplicationService.deleteMessage(messageId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Convert Message entity to MessageResponseDTO.
     */
    private fun Message.toResponseDTO() = MessageResponseDTO(
        id = this.id,
        chatId = this.chatId,
        senderId = this.senderId,
        content = this.content,
        messageType = this.type.value.name,
        isEdited = this.isEdited,
        editedAt = this.editedAt,
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt,
        replyToId = this.replyToId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
