package com.realtimehub.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "participants")
class ParticipantEntity(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID? = null,

    @Column(name = "chat_id", nullable = false, columnDefinition = "uuid")
    var chatId: UUID = UUID.randomUUID(),

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    var userId: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var role: ParticipantRoleEntity = ParticipantRoleEntity.MEMBER,

    @Column(name = "joined_at", nullable = false)
    var joinedAt: Instant = Instant.now(),
)

enum class ParticipantRoleEntity {
    OWNER,
    ADMIN,
    MEMBER,
}
