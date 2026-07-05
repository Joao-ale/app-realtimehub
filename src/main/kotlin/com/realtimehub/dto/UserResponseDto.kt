package com.realtimehub.dto

import com.realtimehub.domain.model.UserStatus
import java.time.Instant
import java.time.LocalDateTime

data class UserResponseDto(
	val id: String,
	val name: String,
	val email: String,
	val status: UserStatus,
	val lastSeenAt: LocalDateTime?,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)

