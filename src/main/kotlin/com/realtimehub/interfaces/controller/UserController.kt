package com.realtimehub.interfaces.controller

import com.realtimehub.application.service.UserApplicationService
import com.realtimehub.domain.user.entity.User
import com.realtimehub.interfaces.dto.user.UserRequestDTO
import com.realtimehub.interfaces.dto.user.UserResponseDTO
import com.realtimehub.interfaces.dto.user.UserUpdateDTO
import com.realtimehub.shared.domain.Result
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST Controller for User operations.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints")
class UserController(
    private val userApplicationService: UserApplicationService,
) {

    /**
     * Create a new user.
     */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Register a new user with email, username and password")
    suspend fun createUser(
        @RequestBody request: UserRequestDTO,
    ): ResponseEntity<Any> {
        val result = userApplicationService.createUser(
            email = request.email,
            username = request.username,
            password = request.password ?: "",
            fullName = request.fullName,
            profilePhotoUrl = request.profilePhotoUrl,
            bio = request.bio,
        )

        return when (result) {
            is Result.Success -> ResponseEntity.status(HttpStatus.CREATED)
                .body(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Get user by ID.
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieve user information by user ID")
    suspend fun getUserById(
        @PathVariable userId: String,
    ): ResponseEntity<Any> {
        val result = userApplicationService.getUserById(userId)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Get user by email.
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieve user information by email")
    suspend fun getUserByEmail(
        @PathVariable email: String,
    ): ResponseEntity<Any> {
        val result = userApplicationService.getUserByEmail(email)

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Update user profile.
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Update user profile information")
    suspend fun updateUserProfile(
        @PathVariable userId: String,
        @RequestBody request: UserUpdateDTO,
    ): ResponseEntity<Any> {
        val result = userApplicationService.updateUserProfile(
            userId = userId,
            fullName = request.fullName,
            profilePhotoUrl = request.profilePhotoUrl,
            bio = request.bio,
        )

        return when (result) {
            is Result.Success -> ResponseEntity.ok(result.data.toResponseDTO())
            is Result.Failure -> ResponseEntity.badRequest()
                .body(mapOf("error" to result.error.message))
        }
    }

    /**
     * Convert User entity to UserResponseDTO.
     */
    private fun User.toResponseDTO() = UserResponseDTO(
        id = this.id,
        email = this.email.value,
        username = this.username.value,
        fullName = this.fullName,
        profilePhotoUrl = this.profilePhotoUrl,
        bio = this.bio,
        status = this.status.value.name,
        lastSeen = this.lastSeen,
        isActive = this.isActive,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
