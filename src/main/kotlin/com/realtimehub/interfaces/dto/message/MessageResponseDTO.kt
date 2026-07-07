package com.realtimehub.interfaces.dto.message

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * Message Response DTO for API responses.
 */
@Schema(description = "Response DTO for message operations")
data class MessageResponseDTO(
    @field:Schema(description = "Message ID (UUID)")
    @JsonProperty("id")
    val id: String,

    @field:Schema(description = "Chat ID (UUID)")
    @JsonProperty("chat_id")
    val chatId: String,

    @field:Schema(description = "Sender user ID (UUID)")
    @JsonProperty("sender_id")
    val senderId: String,

    @field:Schema(description = "Message content")
    @JsonProperty("content")
    val content: String? = null,

    @field:Schema(description = "Message type", allowableValues = ["TEXT", "IMAGE", "FILE", "AUDIO", "VIDEO", "EMOJI"])
    @JsonProperty("message_type")
    val messageType: String,

    @field:Schema(description = "Whether message was edited")
    @JsonProperty("is_edited")
    val isEdited: Boolean,

    @field:Schema(description = "Edit timestamp", nullable = true)
    @JsonProperty("edited_at")
    val editedAt: LocalDateTime? = null,

    @field:Schema(description = "Whether message was deleted")
    @JsonProperty("is_deleted")
    val isDeleted: Boolean,

    @field:Schema(description = "Delete timestamp", nullable = true)
    @JsonProperty("deleted_at")
    val deletedAt: LocalDateTime? = null,

    @field:Schema(description = "ID of message being replied to", nullable = true)
    @JsonProperty("reply_to_id")
    val replyToId: String? = null,

    @field:Schema(description = "Message creation timestamp")
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @field:Schema(description = "Last update timestamp")
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
)
