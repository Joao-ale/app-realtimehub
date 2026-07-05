package com.realtimehub.domain.model

import java.time.Instant
import java.util.UUID

enum class ParticipantRole {
    OWNER,
    ADMIN,
    MEMBER,
}

data class Participant(
    val id: UUID,
    val chatId: UUID,
    val userId: UUID,
    val role: ParticipantRole,
    val joinedAt: Instant,
)
