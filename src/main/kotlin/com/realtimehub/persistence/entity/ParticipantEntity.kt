package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

/**
 * JPA Entity for Participant persistence.
 * Maps to the 'participants' table in the database.
 * Represents users that are part of a chat.
 */
@Entity
@Table(
    name = "participants",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_participants_chat_user",
            columnNames = ["chat_id", "user_id"],
        ),
    ],
)
data class ParticipantEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "chat_id", length = 36, nullable = false)
    val chatId: String = "",

    @Column(name = "user_id", length = 36, nullable = false)
    val userId: String = "",

    @Column(name = "role", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    val role: ParticipantRoleEnum = ParticipantRoleEnum.MEMBER,

    @Column(name = "joined_at", nullable = false)
    val joinedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "left_at", nullable = true)
    val leftAt: LocalDateTime? = null,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,

    @Column(name = "muted_until", nullable = true)
    val mutedUntil: LocalDateTime? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor() : this(
        id = "",
        chatId = "",
        userId = "",
        role = ParticipantRoleEnum.MEMBER,
        joinedAt = LocalDateTime.now(),
        leftAt = null,
        isActive = true,
        mutedUntil = null,
        createdAt = LocalDateTime.now(),
    )
}

enum class ParticipantRoleEnum {
    OWNER,
    ADMIN,
    MEMBER,
}
