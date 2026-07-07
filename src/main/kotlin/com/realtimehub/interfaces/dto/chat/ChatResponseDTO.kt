package com.realtimehub.interfaces.dto.chat

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * Chat Response DTO for API responses.
 */
@Schema(description = "Response DTO for chat operations")
data class ChatResponseDTO(
    @field:Schema(description = "Chat ID (UUID)", example = "a9b65e9d-2133-4326-a474-0ad3452ca7ab")
    @JsonProperty("id")
    val id: String,

    @field:Schema(description = "Chat name", nullable = true)
    @JsonProperty("name")
    val name: String? = null,

    @field:Schema(description = "Chat description", nullable = true)
    @JsonProperty("description")
    val description: String? = null,

    @field:Schema(description = "Chat type", allowableValues = ["PRIVATE", "GROUP"])
    @JsonProperty("chat_type")
    val chatType: String,

    @field:Schema(description = "Chat creator ID (UUID)")
    @JsonProperty("creator_id")
    val creatorId: String,

    @field:Schema(description = "Group photo URL", nullable = true)
    @JsonProperty("group_photo_url")
    val groupPhotoUrl: String? = null,

    @field:Schema(description = "Is chat active")
    @JsonProperty("is_active")
    val isActive: Boolean,

    @field:Schema(description = "Chat creation timestamp")
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @field:Schema(description = "Last update timestamp")
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
)
