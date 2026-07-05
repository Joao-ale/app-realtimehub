package com.realtimehub.service

import com.realtimehub.domain.model.User
import com.realtimehub.domain.model.UserStatus
import com.realtimehub.domain.port.UserRepository
import com.realtimehub.dto.UpdateUserRequestDto
import com.realtimehub.dto.UserRequestDto
import com.realtimehub.dto.UserResponseDto
import com.realtimehub.persistence.mapper.UserMapper
import com.realtimehub.utils.generateId
import com.realtimehub.utils.generatePasswordHash
import com.realtimehub.utils.validatePassword
import org.springframework.stereotype.Service
import java.time.LocalDateTime
@Service
class UserService(
	private val userRepository: UserRepository,
	private val userMapper: UserMapper
) {
	fun getUsers(): List<UserResponseDto> {
		val users = userRepository.findUsers()
		return users.map { user ->
			userMapper.toResponseDto(user)
		}
	}

	fun getUserById(userId: String): UserResponseDto {
		val user = userRepository.findById(userId)
		return userMapper.toResponseDto(user)
	}

	fun createUser(userRequest: UserRequestDto): UserResponseDto {
		val user = User(
			id = generateId(),
			name = userRequest.name,
			email = userRequest.email,
			passwordHash = generatePasswordHash(userRequest.password),
			status = UserStatus.ONLINE,
			lastSeenAt = LocalDateTime.now(),
			createdAt = LocalDateTime.now(),
			updatedAt = LocalDateTime.now()
		)
		userRepository.save(user)
		return userMapper.toResponseDto(user)
	}

	fun getUserByEmail(email: String): UserResponseDto {
		val user = userRepository.findByEmail(email)
		return userMapper.toResponseDto(user)
	}

	fun updateUserName(updateUser: UpdateUserRequestDto): UserResponseDto {
		val user = userRepository.findById(updateUser.id)
		val newUser = user.copy(name = updateUser.name!!)

		userRepository.save(newUser)

		return userMapper.toResponseDto(newUser)
	}

	fun updateUserEmail(updateUser: UpdateUserRequestDto): UserResponseDto {
		val user = userRepository.findById(updateUser.id)
		val newUser = user.copy(email = updateUser.email!!)

		userRepository.save(newUser)

		return userMapper.toResponseDto(newUser)
	}

	fun updateUserPassword(updateUser: UpdateUserRequestDto): UserResponseDto {
		val user = userRepository.findById(updateUser.id)
		val updatePassword = updateUser.password!!
		validatePassword(updatePassword, user.passwordHash)
		val newEncondePassword = generatePasswordHash(updatePassword)
		val newUser = user.copy(name = newEncondePassword)

		userRepository.save(newUser)

		return userMapper.toResponseDto(newUser)
	}

}
