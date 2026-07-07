package com.realtimehub.infrastructure.repository

import com.realtimehub.domain.user.entity.User
import com.realtimehub.domain.user.repository.UserRepository
import com.realtimehub.domain.user.valueobject.Email
import com.realtimehub.domain.user.valueobject.Username
import com.realtimehub.persistence.converter.UserConverter
import com.realtimehub.persistence.repository.UserJpaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 * Implementation of UserRepository domain interface.
 * Bridges domain logic with JPA persistence layer.
 */
@Component
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
    private val userConverter: UserConverter,
) : UserRepository {

    override suspend fun save(aggregate: User): User = withContext(Dispatchers.IO) {
        val entity = userConverter.toEntity(aggregate)
        val savedEntity = userJpaRepository.save(entity)
        userConverter.toDomain(savedEntity)
    }

    override suspend fun findById(id: String): User? = withContext(Dispatchers.IO) {
        val entity = userJpaRepository.findById(id).orElse(null) ?: return@withContext null
        userConverter.toDomain(entity)
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext if (userJpaRepository.existsById(id)) {
            userJpaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    override suspend fun findByEmail(email: Email): User? = withContext(Dispatchers.IO) {
        val entity = userJpaRepository.findByEmail(email.value) ?: return@withContext null
        userConverter.toDomain(entity)
    }

    override suspend fun findByUsername(username: Username): User? = withContext(Dispatchers.IO) {
        val entity = userJpaRepository.findByUsername(username.value) ?: return@withContext null
        userConverter.toDomain(entity)
    }

    override suspend fun existsByEmail(email: Email): Boolean = withContext(Dispatchers.IO) {
        userJpaRepository.existsByEmail(email.value)
    }

    override suspend fun existsByUsername(username: Username): Boolean = withContext(Dispatchers.IO) {
        userJpaRepository.existsByUsername(username.value)
    }
}
