package com.realtimehub.domain.user.entity

import com.realtimehub.domain.user.event.UserCreatedEvent
import com.realtimehub.domain.user.event.UserStatusChangedEvent
import com.realtimehub.shared.domain.AggregateRoot
import com.realtimehub.shared.utils.DateTimeUtils
import java.time.LocalDateTime
import java.util.UUID

class User(
    override val id: String,
    val email: String,
    val username: String,
    val password: String,
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

        fun create(
            email: String,
            username: String,
            password: String,
            fullName: String? = null,
            profilePhotoUrl: String? = null,
            bio: String? = null,
        ): User {
            val userId = UUID.randomUUID().toString()
            val now = DateTimeUtils.now()
            val user = User(
                id = userId,
                email = email,
                username = username,
                password = password,
                fullName = fullName,
                profilePhotoUrl = profilePhotoUrl,
                bio = bio,
                status = UserStatus.OFFLLINE,
                lastSeen = now,
                isActive = true,
                createdAt = now,
                updatedAt = now,
            )

            user.registerEvent(
                UserCreatedEvent(
                    aggregateId = userId,
                    email = email,
                    username = username,
                    fullName = fullName,
                )
            )

            return user
        }
    }


    fun changeStatus(newStatus: UserStatus): User {
        if (this.status == newStatus) {
            return this
        }
        val now = DateTimeUtils.now()
        val updated = User(
            id = this.id,
            email = this.email,
            username = this.username,
            password = this.password,
            fullName = this.fullName,
            profilePhotoUrl = this.profilePhotoUrl,
            bio = this.bio,
            status = newStatus,
            lastSeen = now,
            isActive = this.isActive,
            createdAt = this.createdAt,
            updatedAt = now,
        )

        updated.registerEvent(
            UserStatusChangedEvent(
                aggregateId = this.id,
                previousStatus = this.status.toString(),
                newStatus = newStatus.toString(),
            )
        )

        return updated
    }

    fun updateProfile(
        fullName: String? = null,
        profilePhotoUrl: String? = null,
        bio: String? = null,
    ): User {
        val now = DateTimeUtils.now()
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
            updatedAt = now,
        )
    }


    fun deactivate(): User {
        val now = DateTimeUtils.now()
        return User(
            id = this.id,
            email = this.email,
            username = this.username,
            password = this.password,
            fullName = this.fullName,
            profilePhotoUrl = this.profilePhotoUrl,
            bio = this.bio,
            status = UserStatus.OFFLLINE,
            lastSeen = this.lastSeen,
            isActive = false,
            createdAt = this.createdAt,
            updatedAt = now,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
