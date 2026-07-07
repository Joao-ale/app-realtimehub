package com.realtimehub.interfaces.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * User Request DTO for creating or updating users.
 */
@Schema(description = "Request DTO for user creation and updates")
data class UserRequestDTO(
    @field:Schema(description = "User email address", example = "user@example.com")
    @JsonProperty("email")
    val email: String,

    @field:Schema(description = "Username (3-50 characters)", example = "johndoe")
    @JsonProperty("username")
    val username: String,

    @field:Schema(description = "User password (min 8 chars, uppercase, lowercase, digit, special char)", example = "Password@123")
    @JsonProperty("password")
    val password: String? = null,

    @field:Schema(description = "User full name", example = "John Doe", nullable = true)
    @JsonProperty("full_name")
    val fullName: String? = null,

    @field:Schema(description = "Profile photo URL", nullable = true)
    @JsonProperty("profile_photo_url")
    val profilePhotoUrl: String? = null,

    @field:Schema(description = "User bio", nullable = true)
    @JsonProperty("bio")
    val bio: String? = null,
)
