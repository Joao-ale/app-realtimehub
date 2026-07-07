package com.realtimehub.application.service

import com.realtimehub.domain.user.entity.User
import com.realtimehub.domain.user.repository.UserRepository
import com.realtimehub.domain.user.valueobject.Email
import com.realtimehub.domain.user.valueobject.Password
import com.realtimehub.domain.user.valueobject.Username
import com.realtimehub.infrastructure.event.DomainEventPublisher
import com.realtimehub.shared.domain.DomainError
import com.realtimehub.shared.domain.Result
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Application service for user operations.
 * Orchestrates domain logic and coordinates with repositories.
 */
@Service
class UserApplicationService(
    private val userRepository: UserRepository,
    private val domainEventPublisher: DomainEventPublisher,
) {

    /**
     * Create a new user.
     */
    @Transactional
    suspend fun createUser(
        email: String,
        username: String,
        password: String,
        fullName: String? = null,
        profilePhotoUrl: String? = null,
        bio: String? = null,
    ): Result<User> {
        return try {
            // Validate email format
            val emailVo = Email.create(email)

            // Check if email already exists
            if (userRepository.existsByEmail(emailVo)) {
                return Result.Failure(
                    DomainError.DuplicateError(
                        message = "Email already registered: $email",
                    ),
                )
            }

            // Validate username format
            val usernameVo = Username.create(username)

            // Check if username already exists
            if (userRepository.existsByUsername(usernameVo)) {
                return Result.Failure(
                    DomainError.DuplicateError(
                        message = "Username already taken: $username",
                    ),
                )
            }

            // Validate and hash password
            val passwordVo = Password.create(password)

            // Create user aggregate
            val user = User.create(
                email = emailVo,
                username = usernameVo,
                password = passwordVo,
                fullName = fullName,
                profilePhotoUrl = profilePhotoUrl,
                bio = bio,
            )

            // Save user
            val savedUser = userRepository.save(user)

            // Publish domain events
            domainEventPublisher.publishAll(savedUser.getDomainEvents())
            savedUser.clearDomainEvents()

            Result.Success(savedUser)
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

    /**
     * Get user by ID.
     */
    @Transactional(readOnly = true)
    suspend fun getUserById(userId: String): Result<User> {
        return try {
            val user = userRepository.findById(userId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "User not found: $userId",
                    ),
                )
            Result.Success(user)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to get user: ${e.message}",
                ),
            )
        }
    }

    /**
     * Get user by email.
     */
    @Transactional(readOnly = true)
    suspend fun getUserByEmail(email: String): Result<User> {
        return try {
            val emailVo = Email.create(email)
            val user = userRepository.findByEmail(emailVo)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "User not found with email: $email",
                    ),
                )
            Result.Success(user)
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

    /**
     * Update user profile.
     */
    @Transactional
    suspend fun updateUserProfile(
        userId: String,
        fullName: String? = null,
        profilePhotoUrl: String? = null,
        bio: String? = null,
    ): Result<User> {
        return try {
            val user = userRepository.findById(userId)
                ?: return Result.Failure(
                    DomainError.NotFoundError(
                        message = "User not found: $userId",
                    ),
                )

            val updatedUser = user.updateProfile(
                fullName = fullName,
                profilePhotoUrl = profilePhotoUrl,
                bio = bio,
            )

            val savedUser = userRepository.save(updatedUser)
            domainEventPublisher.publishAll(savedUser.getDomainEvents())
            savedUser.clearDomainEvents()

            Result.Success(savedUser)
        } catch (e: Exception) {
            Result.Failure(
                DomainError.BusinessRuleError(
                    message = "Failed to update user: ${e.message}",
                ),
            )
        }
    }
}
