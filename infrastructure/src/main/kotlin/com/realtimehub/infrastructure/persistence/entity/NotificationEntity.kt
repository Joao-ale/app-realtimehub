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
@Table(name = "notifications")
class NotificationEntity(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID? = null,

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    var userId: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    var type: NotificationTypeEntity = NotificationTypeEntity.NEW_MESSAGE,

    @Column(nullable = false)
    var title: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    var body: String = "",

    @Column(name = "reference_id", columnDefinition = "uuid")
    var referenceId: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: NotificationStatusEntity = NotificationStatusEntity.PENDING,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "read_at")
    var readAt: Instant? = null,
)

enum class NotificationTypeEntity {
    NEW_MESSAGE,
    MENTION,
    CHAT_INVITE,
    SYSTEM,
}

enum class NotificationStatusEntity {
    PENDING,
    DELIVERED,
    READ,
}
