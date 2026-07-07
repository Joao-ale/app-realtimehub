package com.realtimehub.interfaces.dto.message

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Message Request DTO for sending messages.
 */
@Schema(description = "Request DTO for sending messages")
data class MessageRequestDTO(
    @field:Schema(description = "Message content", example = "Hello, world!")
    @JsonProperty("content")
    val content: String,

    @field:Schema(description = "Message type", allowableValues = ["TEXT", "IMAGE", "FILE", "AUDIO", "VIDEO", "EMOJI"], example = "TEXT")
    @JsonProperty("message_type")
    val messageType: String = "TEXT",

    @field:Schema(description = "ID of message being replied to", nullable = true)
    @JsonProperty("reply_to_id")
    val replyToId: String? = null,
)
