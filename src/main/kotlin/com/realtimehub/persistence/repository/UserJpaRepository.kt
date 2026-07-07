package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for User entity.
 */
@Repository
interface UserJpaRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
    fun findByUsername(username: String): UserEntity?
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun findByIsActiveTrue(): List<UserEntity>
}
