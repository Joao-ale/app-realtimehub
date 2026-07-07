package com.realtimehub.interfaces.controller

import com.realtimehub.application.service.NotificationApplicationService
import com.realtimehub.domain.notification.entity.Notification
import com.realtimehub.interfaces.dto.notification.NotificationResponseDTO
import com.realtimehub.shared.domain.Result
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * REST Controller for Notification operations.
 */
@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "Notification management endpoints")
class NotificationController(
    private val notificationApplicationService: NotificationApplicationService,
) {

    /**
     * Get user notifications.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user notifications", description = "Get all notifications for a user")
    suspend fun getUserNotifications(
        @PathVariable userId: String,
    ): ResponseEntity<Any> {
        val result = notificationApplicationService.getUserNotifications(userId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.map { it.toResponseDTO() })
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Get unread notifications.
     */
    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications", description = "Get unread notifications for a user")
    suspend fun getUnreadNotifications(
        @PathVariable userId: String,
    ): ResponseEntity<Any> {
        val result = notificationApplicationService.getUnreadNotifications(userId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.map { it.toResponseDTO() })
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Mark notification as read.
     */
    @PutMapping("/{notificationId}/read")
    @Operation(summary = "Mark notification as read", description = "Mark a notification as read")
    suspend fun markNotificationAsRead(
        @PathVariable notificationId: String,
    ): ResponseEntity<Any> {
        val result = notificationApplicationService.markNotificationAsRead(notificationId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Convert Notification entity to NotificationResponseDTO.
     */
    private fun Notification.toResponseDTO() = NotificationResponseDTO(
        id = this.id,
        userId = this.userId,
        title = this.title,
        description = this.description,
        notificationType = this.type.value.name,
        relatedUserId = this.relatedUserId,
        relatedMessageId = this.relatedMessageId,
        relatedChatId = this.relatedChatId,
        isRead = this.isRead,
        readAt = this.readAt,
        createdAt = this.createdAt,
    )
}
