package com.realtimehub.domain.port

import com.realtimehub.domain.user.entity.User

interface UserRepository {
    suspend fun save(user: User): User
    suspend fun findById(id: String): User?
    suspend fun delete(id: String): Boolean
    suspend fun findByEmail(email: String): User?
    suspend fun findByUsername(username: String): User?
    suspend fun existsByEmail(email: String): Boolean
    suspend fun existsByUsername(username: String): Boolean
}
