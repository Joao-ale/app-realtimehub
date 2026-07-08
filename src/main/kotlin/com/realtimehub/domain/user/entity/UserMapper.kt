package com.realtimehub.domain.user.entity

import com.realtimehub.domain.chat.entity.Chat
import com.realtimehub.interfaces.dto.chat.ChatResponseDTO
import com.realtimehub.interfaces.dto.user.UserResponseDTO

object UserMapper {
	fun toResponseDTO(user: User) = UserResponseDTO(
		id = user.id,
		username = user.username,
		email = user.email,
		isActive = user.isActive,
		createdAt = user.createdAt,
		updatedAt = user.updatedAt,
		lastSeen = user.lastSeen,
		profilePhotoUrl = user.profilePhotoUrl,
		fullName = user.fullName,
		bio = user.bio,
		status = user.status.name,
	)
}