package com.realtimehub.api

import com.realtimehub.domain.model.User
import com.realtimehub.infra.persistence.UserRepository
import com.realtimehub.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import jakarta.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/auth")
class AuthController(
  private val userRepository: UserRepository,
  private val encoder: BCryptPasswordEncoder,
  private val authenticationManager: AuthenticationManager,
  private val tokenService: TokenService
) {

  @PostMapping("/register")
  fun register(@RequestBody req: RegisterRequest): ResponseEntity<Any> {
    if (userRepository.findByEmail(req.email) != null) {
      return ResponseEntity.badRequest().body(mapOf("error" to "Email already exists"))
    }
    val user = User(
      id = UUID.randomUUID(),
      name = req.name,
      email = req.email,
      password = encoder.encode(req.password)
    )
    userRepository.save(user)
    return ResponseEntity.ok(mapOf("id" to user.id))
  }

  @PostMapping("/login")
  fun login(@RequestBody req: LoginRequest, servletRequest: HttpServletRequest): ResponseEntity<Any> {
    try {
      authenticationManager.authenticate(UsernamePasswordAuthenticationToken(req.email, req.password))
    } catch (ex: BadCredentialsException) {
      return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
    }
    val user = userRepository.findByEmail(req.email) ?: return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))

    val ip = servletRequest.remoteAddr
    val ua = servletRequest.getHeader("User-Agent")

    val tokens = tokenService.createTokens(user.id, req.deviceId, ip, ua)
    return ResponseEntity.ok(TokenResponse(tokens.accessToken, tokens.refreshToken))
  }

  @PostMapping("/refresh")
  fun refresh(@RequestBody req: RefreshRequest): ResponseEntity<Any> {
    val userId = tokenService.validateRefreshToken(req.refreshToken) ?: return ResponseEntity.status(401).body(mapOf("error" to "Invalid refresh token"))
    // revoke old refresh token
    tokenService.revokeRefreshToken(req.refreshToken)
    val tokens = tokenService.createTokens(userId)
    return ResponseEntity.ok(TokenResponse(tokens.accessToken, tokens.refreshToken))
  }

  @PostMapping("/logout")
  fun logout(@RequestBody req: LogoutRequest): ResponseEntity<Any> {
    val userId = try { tokenService.validateRefreshToken(req.refreshToken) } catch (_: Exception) { null }
    if (req.revokeAll && userId != null) {
      tokenService.revokeAllRefreshTokensForUser(userId)
      return ResponseEntity.ok(mapOf("ok" to true))
    }
    try {
      tokenService.revokeRefreshToken(req.refreshToken)
    } catch (_: Exception) { /* ignore */ }
    return ResponseEntity.ok(mapOf("ok" to true))
  }
}

data class RegisterRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String, val deviceId: String? = null)
data class TokenResponse(val accessToken: String, val refreshToken: String)
data class RefreshRequest(val refreshToken: String)
data class LogoutRequest(val refreshToken: String, val revokeAll: Boolean = false)
