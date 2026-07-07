package com.realtimehub.application.service

import com.realtimehub.domain.notification.entity.Notification
import com.realtimehub.domain.notification.repository.NotificationRepository
import com.realtimehub.domain.notification.valueobject.NotificationType
import com.realtimehub.infrastructure.event.DomainEventPublisher
import com.realtimehub.shared.domain.DomainError
import com.realtimehub.shared.domain.Result
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Application service for notification operations.
 * Orchestrates domain logic and coordinates with repositories.
 */
@Service
class NotificationApplicationService(
    private val notificationRepository: NotificationRepository,
    private val domainEventPublisher: DomainEventPublisher,
) {

    /**
     * Create and send a notification.
     */
    @Transactional
    suspend fun createNotification(
        userId: String,
        title: String,
        description: String? = null,
        notificationType: String,
        relatedUserId: String? = null,
        relatedMessageId: String? = null,
        relatedChatId: String? = null,
    ): Result<Notification> {
        return try {
            val type = try {
                NotificationType(
                    value = NotificationType.Type.valueOf(notificationType.uppercase()),
                )
            } catch (e: Exception) {
                NotificationType.systemMessage()
            }

            val notification = Notification.create(
                userId = userId,
                title = title,
                description = description,
                type = type,
                relatedUserId = relatedUserId,
                relatedMessageId = relatedMessageId,
                relatedChatId = relatedChatId,
            )

            val savedNotification = notificationRepository.save(notification)

            Result.Success(savedNotification)
        } catch (e: IllegalArgumentException) {
            Result.Failure(
                DomainError.ValidationError(
                    message = e.message ?: "Invalid notification data",
                ),
            )
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to create notification: ${e.message}",
                ),
            )
        }
    }

    /**
     * Get all notifications for a user.
     */
    @Transactional(readOnly = true)
    suspend fun getUserNotifications(userId: String): Result<List<Notification>> {
        return try {
            val notifications = notificationRepository.findByUserId(userId)
            Result.Success(notifications)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get notifications: ${e.message}",
                ),
            )
        }
    }

    /**
     * Get unread notifications for a user.
     */
    @Transactional(readOnly = true)
    suspend fun getUnreadNotifications(userId: String): Result<List<Notification>> {
        return try {
            val notifications = notificationRepository.findUnreadByUserId(userId)
            Result.Success(notifications)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get unread notifications: ${e.message}",
                ),
            )
        }
    }

    /**
     * Mark notification as read.
     */
    @Transactional
    suspend fun markNotificationAsRead(notificationId: String): Result<Notification> {
        return try {
            val notification = notificationRepository.findById(notificationId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Notification not found: $notificationId",
                    ),
                )

            val readNotification = notification.markAsRead()
            val savedNotification = notificationRepository.save(readNotification)

            Result.Success(savedNotification)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to mark notification as read: ${e.message}",
                ),
            )
        }
    }
}
