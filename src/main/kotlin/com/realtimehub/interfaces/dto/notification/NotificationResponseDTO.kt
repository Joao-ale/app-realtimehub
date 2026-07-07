package com.realtimehub.interfaces.dto.notification

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * Notification Response DTO for API responses.
 */
@Schema(description = "Response DTO for notification operations")
data class NotificationResponseDTO(
    @field:Schema(description = "Notification ID (UUID)")
    @JsonProperty("id")
    val id: String,

    @field:Schema(description = "User ID (UUID)")
    @JsonProperty("user_id")
    val userId: String,

    @field:Schema(description = "Notification title")
    @JsonProperty("title")
    val title: String,

    @field:Schema(description = "Notification description", nullable = true)
    @JsonProperty("description")
    val description: String? = null,

    @field:Schema(description = "Notification type", allowableValues = [
        "NEW_MESSAGE", "MESSAGE_REACTION", "USER_MENTIONED", "CHAT_INVITATION",
        "PARTICIPANT_ADDED", "PARTICIPANT_REMOVED", "CHAT_NAME_CHANGED", "SYSTEM_MESSAGE"
    ])
    @JsonProperty("notification_type")
    val notificationType: String,

    @field:Schema(description = "Related user ID", nullable = true)
    @JsonProperty("related_user_id")
    val relatedUserId: String? = null,

    @field:Schema(description = "Related message ID", nullable = true)
    @JsonProperty("related_message_id")
    val relatedMessageId: String? = null,

    @field:Schema(description = "Related chat ID", nullable = true)
    @JsonProperty("related_chat_id")
    val relatedChatId: String? = null,

    @field:Schema(description = "Whether notification was read")
    @JsonProperty("is_read")
    val isRead: Boolean,

    @field:Schema(description = "Read timestamp", nullable = true)
    @JsonProperty("read_at")
    val readAt: LocalDateTime? = null,

    @field:Schema(description = "Creation timestamp")
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
)
