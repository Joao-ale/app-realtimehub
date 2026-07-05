package com.realtimehub.controller

import com.realtimehub.dto.UpdateUserRequestDto
import com.realtimehub.dto.UserRequestDto
import com.realtimehub.dto.UserResponseDto
import com.realtimehub.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Users", description = "Users endpoint")
class UserController(
	private val userService : UserService
) {
	@GetMapping("/users")
	fun getUsers(): ResponseEntity<List<UserResponseDto>> {
		return ResponseEntity.ok(userService.getUsers())
	}

	@GetMapping("/user/{userId}")
	fun getUserByUserId(@PathVariable userId: String): ResponseEntity<UserResponseDto>{
		return ResponseEntity.ok(userService.getUserById(userId))
	}

	@GetMapping("/user/email")
	fun getUserByEmail(@RequestParam email: String): ResponseEntity<UserResponseDto>{
		return ResponseEntity.ok(userService.getUserByEmail(email))
	}

	@PostMapping("/user")
	fun createUser(@RequestBody userRequest: UserRequestDto): ResponseEntity<UserResponseDto>{
		return ResponseEntity.ok(userService.createUser(userRequest))
	}

	@PatchMapping("/user/name")
	fun updateName(@RequestBody userUpdate: UpdateUserRequestDto): ResponseEntity<UserResponseDto>{
		return ResponseEntity.ok(userService.updateUserName(userUpdate))
	}

	@PatchMapping("/user/email")
	fun updateEmail(@RequestBody userUpdate: UpdateUserRequestDto): ResponseEntity<UserResponseDto>{
		return ResponseEntity.ok(userService.updateUserEmail(userUpdate))
	}

	@PatchMapping("/user/password")
	fun updatePassword(@RequestBody userUpdate: UpdateUserRequestDto): ResponseEntity<UserResponseDto>{
		return ResponseEntity.ok(userService.updateUserPassword(userUpdate))
	}
}
