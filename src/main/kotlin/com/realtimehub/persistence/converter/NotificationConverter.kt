package com.realtimehub.persistence.converter

import com.realtimehub.domain.notification.entity.Notification
import com.realtimehub.domain.notification.valueobject.NotificationType
import com.realtimehub.persistence.entity.NotificationEntity
import org.springframework.stereotype.Component

/**
 * Converter between Notification domain entity and NotificationEntity JPA entity.
 * Handles bidirectional conversion between domain and persistence models.
 */
@Component
class NotificationConverter {

    /**
     * Convert NotificationEntity (JPA) to Notification (Domain).
     */
    fun toDomain(entity: NotificationEntity): Notification {
        return Notification(
            id = entity.id,
            userId = entity.userId,
            title = entity.title,
            description = entity.description,
            type = NotificationType(
                value = NotificationType.Type.valueOf(entity.notificationType),
            ),
            relatedUserId = entity.relatedUserId,
            relatedMessageId = entity.relatedMessageId,
            relatedChatId = entity.relatedChatId,
            isRead = entity.isRead,
            readAt = entity.readAt,
            createdAt = entity.createdAt,
        )
    }

    /**
     * Convert Notification (Domain) to NotificationEntity (JPA).
     */
    fun toEntity(domain: Notification): NotificationEntity {
        return NotificationEntity(
            id = domain.id,
            userId = domain.userId,
            title = domain.title,
            description = domain.description,
            notificationType = domain.type.value.name,
            relatedUserId = domain.relatedUserId,
            relatedMessageId = domain.relatedMessageId,
            relatedChatId = domain.relatedChatId,
            isRead = domain.isRead,
            readAt = domain.readAt,
            createdAt = domain.createdAt,
        )
    }
}
