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
@Table(name = "chats")
class ChatEntity(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var type: ChatTypeEntity = ChatTypeEntity.PRIVATE,

    @Column
    var name: String? = null,

    @Column(name = "created_by", nullable = false, columnDefinition = "uuid")
    var createdBy: UUID = UUID.randomUUID(),

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
)

enum class ChatTypeEntity {
    PRIVATE,
    GROUP,
}
