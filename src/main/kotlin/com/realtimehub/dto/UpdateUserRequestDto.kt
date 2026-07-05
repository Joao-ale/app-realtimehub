package com.realtimehub.dto

data class UpdateUserRequestDto(
	val id: String,
	val name: String?,
	val email: String?,
	val password: String?
)
