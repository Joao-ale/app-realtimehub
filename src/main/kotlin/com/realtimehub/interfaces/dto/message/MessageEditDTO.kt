package com.realtimehub.interfaces.dto.message

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Message Edit DTO for editing messages.
 */
@Schema(description = "Request DTO for editing messages")
data class MessageEditDTO(
    @field:Schema(description = "New message content", example = "Updated message")
    @JsonProperty("content")
    val content: String,
)
