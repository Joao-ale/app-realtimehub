package com.realtimehub.interfaces.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * User Update DTO for profile updates.
 */
@Schema(description = "Request DTO for updating user profile")
data class UserUpdateDTO(
    @field:Schema(description = "User full name", nullable = true)
    @JsonProperty("full_name")
    val fullName: String? = null,

    @field:Schema(description = "Profile photo URL", nullable = true)
    @JsonProperty("profile_photo_url")
    val profilePhotoUrl: String? = null,

    @field:Schema(description = "User bio", nullable = true)
    @JsonProperty("bio")
    val bio: String? = null,
)
