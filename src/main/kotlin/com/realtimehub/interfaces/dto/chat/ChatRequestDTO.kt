package com.realtimehub.interfaces.dto.chat

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Chat Request DTO for creating or updating chats.
 */
@Schema(description = "Request DTO for chat creation")
data class ChatRequestDTO(
    @field:Schema(description = "Chat name (for group chats)", nullable = true)
    @JsonProperty("name")
    val name: String? = null,

    @field:Schema(description = "Chat description", nullable = true)
    @JsonProperty("description")
    val description: String? = null,

    @field:Schema(description = "Chat type", allowableValues = ["PRIVATE", "GROUP"])
    @JsonProperty("chat_type")
    val chatType: String,

    @field:Schema(description = "Creator ID (for private chats)", nullable = true)
    @JsonProperty("creator_id")
    val creatorId: String? = null,

    @field:Schema(description = "Group photo URL", nullable = true)
    @JsonProperty("group_photo_url")
    val groupPhotoUrl: String? = null,
)
