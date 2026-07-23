package com.realtimehub.application.service

import com.realtimehub.domain.notification.entity.Notification
import com.realtimehub.domain.notification.entity.NotificationMapper
import com.realtimehub.domain.notification.entity.NotificationType
import com.realtimehub.domain.port.NotificationRepository
import com.realtimehub.infrastructure.event.DomainEventPublisher
import com.realtimehub.interfaces.dto.notification.NotificationResponseDTO
import com.realtimehub.shared.domain.DomainError
import com.realtimehub.shared.domain.Result
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class NotificationApplicationService(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: NotificationMapper
) {

    @Transactional
    suspend fun createNotification(
        userId: String,
        title: String,
        description: String? = null,
        notificationType: String,
        relatedUserId: String? = null,
        relatedMessageId: String? = null,
        relatedChatId: String? = null,
    ): Result<NotificationResponseDTO> {
        return try {
            val type = when (notificationType.uppercase()) {
                    "NEW_MESSAGE" -> NotificationType.NEW_MESSAGE
                    "MENTION" -> NotificationType.MENTION
                    "CHAT_INVITE" -> NotificationType.CHAT_INVITE
                    "SYSTEM" -> NotificationType.SYSTEM
                    else -> NotificationType.NEW_MESSAGE
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

            Result.Success(notificationMapper.toResponseDTO(savedNotification))
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

    @Transactional(readOnly = true)
    suspend fun getUserNotifications(userId: String): Result<List<NotificationResponseDTO>> {
        return try {
            val notifications = notificationRepository.findByUserId(userId)
            val notificationsDto = notifications.map {notification ->
                notificationMapper.toResponseDTO(notification)
            }
            Result.Success(notificationsDto)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get notifications: ${e.message}",
                ),
            )
        }
    }


    @Transactional(readOnly = true)
    suspend fun getUnreadNotifications(userId: String): Result<List<NotificationResponseDTO>> {
        return try {
            val notifications = notificationRepository.findUnreadByUserId(userId)
            val notificationsDto = notifications.map { notification ->
                notificationMapper.toResponseDTO(notification)
            }
            Result.Success(notificationsDto)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get unread notifications: ${e.message}",
                ),
            )
        }
    }

    @Transactional
    suspend fun markNotificationAsRead(notificationId: String): Result<NotificationResponseDTO> {
        return try {
            val notification = notificationRepository.findById(notificationId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "Notification not found: $notificationId",
                    ),
                )

            val readNotification = notification.markAsRead()
            val savedNotification = notificationRepository.save(readNotification)

            Result.Success(notificationMapper.toResponseDTO(savedNotification))
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to mark notification as read: ${e.message}",
                ),
            )
        }
    }
}
