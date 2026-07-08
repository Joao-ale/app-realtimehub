package com.realtimehub.domain.port

import com.realtimehub.domain.user.entity.User

interface UserRepository {
    fun save(user: User): User
    fun findById(id: String): User
    fun findByEmail(email: String): User
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun findUsers(): List<User>
}
