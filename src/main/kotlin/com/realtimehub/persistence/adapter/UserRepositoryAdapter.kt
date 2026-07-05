package com.realtimehub.persistence.adapter

import com.realtimehub.domain.model.User
import com.realtimehub.domain.port.UserRepository
import com.realtimehub.persistence.mapper.UserMapper
import com.realtimehub.persistence.repository.JpaUserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserRepositoryAdapter(
	private val jpaUserRepository: JpaUserRepository,
) : UserRepository {
    override fun save(user: User): User =
        jpaUserRepository.save(UserMapper.toEntity(user)).let(UserMapper::toDomain)

    override fun findById(id: UUID): User? =
        jpaUserRepository.findById(id).orElse(null)?.let(UserMapper::toDomain)

    override fun findByEmail(email: String): User? =
        jpaUserRepository.findByEmail(email)?.let(UserMapper::toDomain)

    override fun existsByEmail(email: String): Boolean =
        jpaUserRepository.existsByEmail(email)
}
