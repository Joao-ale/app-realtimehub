package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * JPA Entity for User persistence.
 * Maps to the 'users' table in the database.
 */
@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "email", length = 255, nullable = false, unique = true)
    val email: String = "",

    @Column(name = "username", length = 100, nullable = false, unique = true)
    val username: String = "",

    @Column(name = "password_hash", length = 255, nullable = false)
    val passwordHash: String = "",

    @Column(name = "full_name", length = 255, nullable = true)
    val fullName: String? = null,

    @Column(name = "profile_photo_url", length = 1024, nullable = true)
    val profilePhotoUrl: String? = null,

    @Column(name = "bio", columnDefinition = "TEXT", nullable = true)
    val bio: String? = null,

    @Column(name = "status", length = 20, nullable = false)
    val status: String = "OFFLINE",

    @Column(name = "last_seen", nullable = false)
    val lastSeen: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by", length = 36, nullable = true)
    val createdBy: String? = null,

    @Column(name = "updated_by", length = 36, nullable = true)
    val updatedBy: String? = null,
) {
    constructor() : this(
        id = "",
        email = "",
        username = "",
        passwordHash = "",
        fullName = null,
        profilePhotoUrl = null,
        bio = null,
        status = "OFFLINE",
        lastSeen = LocalDateTime.now(),
        isActive = true,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        createdBy = null,
        updatedBy = null,
    )
}
