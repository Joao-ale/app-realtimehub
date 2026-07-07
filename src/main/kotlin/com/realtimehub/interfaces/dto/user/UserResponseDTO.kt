package com.realtimehub.interfaces.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * User Response DTO for API responses.
 */
@Schema(description = "Response DTO for user operations")
data class UserResponseDTO(
    @field:Schema(description = "User ID (UUID)", example = "a9b65e9d-2133-4326-a474-0ad3452ca7ab")
    @JsonProperty("id")
    val id: String,

    @field:Schema(description = "User email", example = "user@example.com")
    @JsonProperty("email")
    val email: String,

    @field:Schema(description = "Username", example = "johndoe")
    @JsonProperty("username")
    val username: String,

    @field:Schema(description = "User full name", nullable = true)
    @JsonProperty("full_name")
    val fullName: String? = null,

    @field:Schema(description = "Profile photo URL", nullable = true)
    @JsonProperty("profile_photo_url")
    val profilePhotoUrl: String? = null,

    @field:Schema(description = "User bio", nullable = true)
    @JsonProperty("bio")
    val bio: String? = null,

    @field:Schema(description = "User status", example = "ONLINE", allowableValues = ["ONLINE", "OFFLINE", "AWAY", "DND"])
    @JsonProperty("status")
    val status: String,

    @field:Schema(description = "Last seen timestamp")
    @JsonProperty("last_seen")
    val lastSeen: LocalDateTime,

    @field:Schema(description = "Is user active")
    @JsonProperty("is_active")
    val isActive: Boolean,

    @field:Schema(description = "Account creation timestamp")
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @field:Schema(description = "Last update timestamp")
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
)
