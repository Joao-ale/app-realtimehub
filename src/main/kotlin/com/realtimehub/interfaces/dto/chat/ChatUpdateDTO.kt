package com.realtimehub.interfaces.dto.chat

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Chat Update DTO for updating chat information.
 */
@Schema(description = "Request DTO for updating chat information")
data class ChatUpdateDTO(
    @field:Schema(description = "Chat name", nullable = true)
    @JsonProperty("name")
    val name: String? = null,

    @field:Schema(description = "Chat description", nullable = true)
    @JsonProperty("description")
    val description: String? = null,

    @field:Schema(description = "Group photo URL", nullable = true)
    @JsonProperty("group_photo_url")
    val groupPhotoUrl: String? = null,
)
