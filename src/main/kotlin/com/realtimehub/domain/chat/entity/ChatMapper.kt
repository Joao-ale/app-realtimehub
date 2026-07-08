package com.realtimehub.domain.chat.entity

import com.realtimehub.interfaces.dto.chat.ChatRequestDTO
import com.realtimehub.interfaces.dto.chat.ChatResponseDTO
import com.realtimehub.shared.utils.DateTimeUtils
import com.realtimehub.shared.utils.IdUtils.generateId

class ChatMapper {
	fun toResponseDTO(chat: Chat) = ChatResponseDTO(
		id = chat.id,
		name = chat.name,
		description = chat.description,
		chatType = chat.type.name,
		creatorId = chat.creatorId,
		groupPhotoUrl = chat.groupPhotoUrl,
		isActive = chat.isActive,
		createdAt = chat.createdAt,
		updatedAt = chat.updatedAt,
	)

	fun toEntity(request: ChatRequestDTO, id: String) = Chat(
		id = generateId(),
		name = request.name,
		description = request.description,
		type = ChatType.valueOf(request.chatType),
		creatorId = id,
		groupPhotoUrl = request.groupPhotoUrl,
		isActive = true,
		createdAt = DateTimeUtils.now(),
		updatedAt = DateTimeUtils.now(),
	)
}