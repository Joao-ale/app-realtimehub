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
@Table(name = "users")
class UserEntity(
    @Id
    @Column(columnDefinition = "uuid")
    var id: UUID? = null,

    @Column(nullable = false, length = 100)
    var name: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String = "",

    @Column(name = "photo_url")
    var photoUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: UserStatusEntity = UserStatusEntity.OFFLINE,

    @Column(name = "last_seen_at")
    var lastSeenAt: Instant? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
)

enum class UserStatusEntity {
    ONLINE,
    OFFLINE,
    AWAY,
}
