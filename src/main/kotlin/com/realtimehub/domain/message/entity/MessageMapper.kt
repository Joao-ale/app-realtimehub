package com.realtimehub.domain.message.entity

import com.realtimehub.interfaces.dto.message.MessageResponseDTO

class MessageMapper {
	fun toResponseDTO(message: Message) = MessageResponseDTO(
		id = message.id,
		chatId = message.chatId,
		senderId = message.senderId,
		content = message.content,
		messageType = message.type.name,
		isEdited = message.isEdited,
		editedAt = message.editedAt,
		isDeleted = message.isDeleted,
		deletedAt = message.deletedAt,
		replyToId = message.replyToId,
		createdAt = message.createdAt,
		updatedAt = message.updatedAt,
	)
}