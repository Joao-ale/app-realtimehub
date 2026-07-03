package com.realtimehub.infrastructure.persistence.mapper

import com.realtimehub.domain.model.User
import com.realtimehub.domain.model.UserStatus
import com.realtimehub.infrastructure.persistence.entity.UserEntity
import com.realtimehub.infrastructure.persistence.entity.UserStatusEntity

object UserMapper {
    fun toDomain(entity: UserEntity): User =
        User(
            id = entity.id!!,
            name = entity.name,
            email = entity.email,
            passwordHash = entity.passwordHash,
            photoUrl = entity.photoUrl,
            status = entity.status.toDomain(),
            lastSeenAt = entity.lastSeenAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )

    fun toEntity(domain: User): UserEntity =
        UserEntity(
            id = domain.id,
            name = domain.name,
            email = domain.email,
            passwordHash = domain.passwordHash,
            photoUrl = domain.photoUrl,
            status = domain.status.toEntity(),
            lastSeenAt = domain.lastSeenAt,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )

    private fun UserStatusEntity.toDomain(): UserStatus =
        when (this) {
            UserStatusEntity.ONLINE -> UserStatus.ONLINE
            UserStatusEntity.OFFLINE -> UserStatus.OFFLINE
            UserStatusEntity.AWAY -> UserStatus.AWAY
        }

    private fun UserStatus.toEntity(): UserStatusEntity =
        when (this) {
            UserStatus.ONLINE -> UserStatusEntity.ONLINE
            UserStatus.OFFLINE -> UserStatusEntity.OFFLINE
            UserStatus.AWAY -> UserStatusEntity.AWAY
        }
}
