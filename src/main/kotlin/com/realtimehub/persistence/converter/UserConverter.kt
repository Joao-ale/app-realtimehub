package com.realtimehub.persistence.converter

import com.realtimehub.domain.user.entity.User
import com.realtimehub.domain.user.valueobject.Email
import com.realtimehub.domain.user.valueobject.Password
import com.realtimehub.domain.user.valueobject.UserStatus
import com.realtimehub.domain.user.valueobject.Username
import com.realtimehub.persistence.entity.UserEntity
import org.springframework.stereotype.Component

/**
 * Converter between User domain entity and UserEntity JPA entity.
 * Handles bidirectional conversion between domain and persistence models.
 */
@Component
class UserConverter {

    /**
     * Convert UserEntity (JPA) to User (Domain).
     */
    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            email = Email.create(entity.email),
            username = Username.create(entity.username),
            password = Password(
                hash = entity.passwordHash,
            ),
            fullName = entity.fullName,
            profilePhotoUrl = entity.profilePhotoUrl,
            bio = entity.bio,
            status = UserStatus(
                value = UserStatus.Status.valueOf(entity.status),
            ),
            lastSeen = entity.lastSeen,
            isActive = entity.isActive,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }

    /**
     * Convert User (Domain) to UserEntity (JPA).
     */
    fun toEntity(domain: User): UserEntity {
        return UserEntity(
            id = domain.id,
            email = domain.email.value,
            username = domain.username.value,
            passwordHash = domain.password.hash,
            fullName = domain.fullName,
            profilePhotoUrl = domain.profilePhotoUrl,
            bio = domain.bio,
            status = domain.status.value.name,
            lastSeen = domain.lastSeen,
            isActive = domain.isActive,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )
    }
}
