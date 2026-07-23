package com.realtimehub.domain.chat.entity

import java.time.LocalDateTime

class Participant(
    val id: String,
    val chatId: String,
    val userId: String,
    val role: ParticipantRole,
    val joinedAt: LocalDateTime,
    val leftAt: LocalDateTime? = null,
    val isActive: Boolean = true,
    val mutedUntil: LocalDateTime? = null,
    val createdAt: LocalDateTime,
)

enum class ParticipantRole {
    OWNER,
    ADMIN,
    MEMBER,
}
