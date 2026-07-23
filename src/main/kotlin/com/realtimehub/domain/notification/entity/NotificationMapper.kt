package com.realtimehub.domain.notification.entity

import com.realtimehub.interfaces.dto.notification.NotificationResponseDTO

class NotificationMapper {
	fun toResponseDTO(notification: Notification) = NotificationResponseDTO(
	id = notification.id,
	userId = notification.userId,
	title = notification.title,
	description = notification.description,
	notificationType = notification.type,
	relatedUserId = notification.relatedUserId,
	relatedMessageId = notification.relatedMessageId,
	relatedChatId = notification.relatedChatId,
	isRead = notification.isRead,
	readAt = notification.readAt,
	createdAt = notification.createdAt,
	)
}