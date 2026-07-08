package com.realtimehub.persistence.mapper

import com.realtimehub.domain.model.User
import com.realtimehub.domain.model.UserStatus
import com.realtimehub.domain.user.entity.UserStatus
import com.realtimehub.dto.UserRequestDto
import com.realtimehub.dto.UserResponseDto
import com.realtimehub.persistence.entity.UserEntity
import com.realtimehub.persistence.entity.UserStatusEntity
import org.springframework.data.jpa.domain.AbstractPersistable_.id

object UserMapper {

    fun toEntity(domain: User): UserEntity =
        UserEntity(
            id = domain.id,
            name = domain.name,
            email = domain.email,
            passwordHash = domain.passwordHash,
            status = domain.status.toEntity(),
            lastSeenAt = domain.lastSeenAt,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
        )
    fun toResponseDto(user: User) = UserResponseDto(
        id = user.id,
        name = user.name,
        email = user.email,
        status = user.status,
        lastSeenAt = user.lastSeenAt,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt,
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
