package com.realtimehub.interfaces.controller

import com.realtimehub.application.service.ChatApplicationService
import com.realtimehub.domain.chat.entity.Chat
import com.realtimehub.interfaces.dto.chat.ChatRequestDTO
import com.realtimehub.interfaces.dto.chat.ChatResponseDTO
import com.realtimehub.interfaces.dto.chat.ChatUpdateDTO
import com.realtimehub.shared.domain.Result
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST Controller for Chat operations.
 */
@RestController
@RequestMapping("/chats")
@Tag(name = "Chats", description = "Chat management endpoints")
class ChatController(
    private val chatApplicationService: ChatApplicationService,
) {

    /**
     * Create a new chat (group or private).
     */
    @PostMapping
    @Operation(summary = "Create a new chat", description = "Create a new group or private chat")
    suspend fun createChat(
        @RequestBody request: ChatRequestDTO,
    ): ResponseEntity<Any> {
        val result = when (request.chatType.uppercase()) {
            "GROUP" -> chatApplicationService.createGroupChat(
                name = request.name ?: "",
                description = request.description,
                creatorId = "", // TODO: Get from authentication context
                groupPhotoUrl = request.groupPhotoUrl,
            )
            "PRIVATE" -> chatApplicationService.createPrivateChat(
                creatorId = "", // TODO: Get from authentication context
            )
            else -> Result.Failure(
                com.realtimehub.shared.domain.DomainError.ValidationError(
                    message = "Invalid chat type: ${request.chatType}",
                ),
            )
        }

        return when (result) {
            is Result.Success -> ResponseEntity.status(HttpStatus.CREATED)
                .body(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Get chat by ID.
     */
    @GetMapping("/{chatId}")
    @Operation(summary = "Get chat by ID", description = "Retrieve chat information by chat ID")
    suspend fun getChatById(
        @PathVariable chatId: String,
    ): ResponseEntity<Any> {
        val result = chatApplicationService.getChatById(chatId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Get user chats.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user chats", description = "Get all chats for a specific user")
    suspend fun getUserChats(
        @PathVariable userId: String,
    ): ResponseEntity<Any> {
        val result = chatApplicationService.getUserChats(userId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.map { it.toResponseDTO() })
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Update group chat.
     */
    @PutMapping("/{chatId}")
    @Operation(summary = "Update chat", description = "Update group chat information")
    suspend fun updateChat(
        @PathVariable chatId: String,
        @RequestBody request: ChatUpdateDTO,
    ): ResponseEntity<Any> {
        val result = chatApplicationService.updateGroupChat(
            chatId = chatId,
            name = request.name,
            description = request.description,
            groupPhotoUrl = request.groupPhotoUrl,
        )

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Convert Chat entity to ChatResponseDTO.
     */
    private fun Chat.toResponseDTO() = ChatResponseDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        chatType = this.type.value.name,
        creatorId = this.creatorId,
        groupPhotoUrl = this.groupPhotoUrl,
        isActive = this.isActive,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
