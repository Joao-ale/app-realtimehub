package com.realtimehub.infrastructure.persistence.repository

import com.realtimehub.infrastructure.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaUserRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}
