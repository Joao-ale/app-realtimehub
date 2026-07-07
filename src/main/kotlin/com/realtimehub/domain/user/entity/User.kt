package com.realtimehub.domain.user.entity

import com.realtimehub.domain.user.event.UserCreatedEvent
import com.realtimehub.domain.user.event.UserStatusChangedEvent
import com.realtimehub.domain.user.valueobject.Email
import com.realtimehub.domain.user.valueobject.Password
import com.realtimehub.domain.user.valueobject.UserStatus
import com.realtimehub.domain.user.valueobject.UserId
import com.realtimehub.domain.user.valueobject.Username
import com.realtimehub.shared.domain.AggregateRoot
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

/**
 * User aggregate root.
 * This is the main entity for the user domain with all business logic.
 * Uses LocalDateTime with Brasilia timezone (UTC-3).
 */
class User(
    override val id: String,
    val email: Email,
    val username: Username,
    val password: Password,
    val fullName: String?,
    val profilePhotoUrl: String?,
    val bio: String?,
    val status: UserStatus,
    val lastSeen: LocalDateTime,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) : AggregateRoot<String>(id) {

    companion object {
        private val BRASIL_ZONE = ZoneId.of("America/Sao_Paulo")

        /**
         * Factory method to create a new user.
         */
        fun create(
            email: Email,
            username: Username,
            password: Password,
            fullName: String? = null,
            profilePhotoUrl: String? = null,
            bio: String? = null,
        ): User {
            val now = LocalDateTime.now(BRASIL_ZONE)
            val userId = UUID.randomUUID().toString()

            val user = User(
                id = userId,
                email = email,
                username = username,
                password = password,
                fullName = fullName,
                profilePhotoUrl = profilePhotoUrl,
                bio = bio,
                status = UserStatus.offline(),
                lastSeen = now,
                isActive = true,
                createdAt = now,
                updatedAt = now,
            )

            user.registerEvent(
                UserCreatedEvent(
                    aggregateId = userId,
                    email = email.value,
                    username = username.value,
                    fullName = fullName,
                )
            )

            return user
        }
    }

    /**
     * Change user status.
     */
    fun changeStatus(newStatus: UserStatus): User {
        if (this.status == newStatus) {
            return this
        }

        val updated = User(
            id = this.id,
            email = this.email,
            username = this.username,
            password = this.password,
            fullName = this.fullName,
            profilePhotoUrl = this.profilePhotoUrl,
            bio = this.bio,
            status = newStatus,
            lastSeen = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
            isActive = this.isActive,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
        )

        updated.registerEvent(
            UserStatusChangedEvent(
                aggregateId = this.id,
                previousStatus = this.status.value.name,
                newStatus = newStatus.value.name,
            )
        )

        return updated
    }

    /**
     * Update user profile.
     */
    fun updateProfile(
        fullName: String? = null,
        profilePhotoUrl: String? = null,
        bio: String? = null,
    ): User {
        return User(
            id = this.id,
            email = this.email,
            username = this.username,
            password = this.password,
            fullName = fullName ?: this.fullName,
            profilePhotoUrl = profilePhotoUrl ?: this.profilePhotoUrl,
            bio = bio ?: this.bio,
            status = this.status,
            lastSeen = this.lastSeen,
            isActive = this.isActive,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
        )
    }

    /**
     * Verify password.
     */
    fun verifyPassword(plainPassword: String): Boolean = password.matches(plainPassword)

    /**
     * Deactivate user.
     */
    fun deactivate(): User {
        return User(
            id = this.id,
            email = this.email,
            username = this.username,
            password = this.password,
            fullName = this.fullName,
            profilePhotoUrl = this.profilePhotoUrl,
            bio = this.bio,
            status = UserStatus.offline(),
            lastSeen = this.lastSeen,
            isActive = false,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
