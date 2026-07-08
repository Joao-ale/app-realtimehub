package com.realtimehub.application.service

import com.realtimehub.domain.port.UserRepository
import com.realtimehub.domain.user.entity.User
import com.realtimehub.domain.user.entity.UserMapper
import com.realtimehub.infrastructure.event.DomainEventPublisher
import com.realtimehub.interfaces.dto.user.UserRequestDTO
import com.realtimehub.interfaces.dto.user.UserResponseDTO
import com.realtimehub.interfaces.dto.user.UserUpdateDTO
import com.realtimehub.shared.domain.DomainError
import com.realtimehub.shared.domain.Result
import com.realtimehub.shared.utils.PasswordUtils.generatePasswordHash
import com.realtimehub.shared.utils.PasswordUtils.validatePassword
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserApplicationService(
    private val userRepository: UserRepository,
    private val domainEventPublisher: DomainEventPublisher,
    private val userMapper: UserMapper
) {

    @Transactional
    suspend fun createUser(
        request: UserRequestDTO
    ): Result<UserResponseDTO> {
        return try {
            val email = request.email ?: throw IllegalArgumentException("Email is required")
            val username = request.username ?: throw IllegalArgumentException("Username is required")
            val password = request.password ?: throw IllegalArgumentException("Password is required")
            val fullName = request.fullName ?: throw IllegalArgumentException("Full name is required")
            val profilePhotoUrl = request.profilePhotoUrl
            val bio = request.bio

            if (userRepository.existsByEmail(email)) {
                return Result.Failure(
                    DomainError.DuplicateError(
                        message = "Email already registered: $email",
                    ),
                )
            }

            if (userRepository.existsByUsername(username)) {
                return Result.Failure(
                    DomainError.DuplicateError(
                        message = "Username already taken: $username",
                    ),
                )
            }

            val encodePassword = generatePasswordHash(password)

            val user = User.create(
                email = email,
                username = username,
                password = encodePassword,
                fullName = fullName,
                profilePhotoUrl = profilePhotoUrl,
                bio = bio,
            )

            val savedUser = userRepository.save(user)

            domainEventPublisher.publishAll(savedUser.getDomainEvents())
            savedUser.clearDomainEvents()

            Result.Success(userMapper.toResponseDTO(savedUser))
        } catch (e: IllegalArgumentException) {
            Result.Failure(
                DomainError.ValidationError(
                    message = e.message ?: "Invalid user data",
                ),
            )
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to create user: ${e.message}",
                ),
            )
        }
    }

    @Transactional(readOnly = true)
    suspend fun getUserById(userId: String): Result<UserResponseDTO> {
        return try {
            val user = userRepository.findById(userId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "User not found: $userId",
                    ),
                )
            Result.Success(userMapper.toResponseDTO(user))
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get user: ${e.message}",
                ),
            )
        }
    }


    @Transactional(readOnly = true)
    suspend fun getUserByEmail(email: String): Result<UserResponseDTO> {
        return try {
            val user = userRepository.findByEmail(email)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "User not found with email: $email",
                    ),
                )
            Result.Success(userMapper.toResponseDTO(user))
        } catch (e: IllegalArgumentException) {
            Result.Failure(
                DomainError.ValidationError(
                    message = e.message ?: "Invalid email format",
                ),
            )
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get user: ${e.message}",
                ),
            )
        }
    }


    @Transactional
    suspend fun updateUserProfile(
        request: UserUpdateDTO,
        userId: String
    ): Result<UserResponseDTO> {
        return try {
            val user = userRepository.findById(userId)
            val fullName = request.fullName ?: user.fullName
            val profilePhotoUrl = request.profilePhotoUrl ?: user.profilePhotoUrl
            val bio = request.bio ?: user.bio
            validatePassword(request.password, user.password)

            val updatedUser = user.updateProfile(
                fullName = fullName,
                profilePhotoUrl = profilePhotoUrl,
                bio = bio,
            )

            val savedUser = userRepository.save(updatedUser)
            domainEventPublisher.publishAll(savedUser.getDomainEvents())
            savedUser.clearDomainEvents()

            Result.Success(userMapper.toResponseDTO(savedUser))
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to update user: ${e.message}",
                ),
            )
        }
    }
}
