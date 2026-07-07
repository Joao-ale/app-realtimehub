package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * JPA Entity for WebSocket Session persistence.
 * Maps to the 'websocket_sessions' table in the database.
 * Tracks active WebSocket connections for real-time communication.
 */
@Entity
@Table(name = "websocket_sessions")
data class WebSocketSessionEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "user_id", length = 36, nullable = false)
    val userId: String = "",

    @Column(name = "session_id", length = 255, nullable = false, unique = true)
    val sessionId: String = "",

    @Column(name = "device_type", length = 50, nullable = true)
    val deviceType: String? = null,

    @Column(name = "ip_address", length = 45, nullable = true)
    val ipAddress: String? = null,

    @Column(name = "user_agent", columnDefinition = "TEXT", nullable = true)
    val userAgent: String? = null,

    @Column(name = "connected_at", nullable = false)
    val connectedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "disconnected_at", nullable = true)
    val disconnectedAt: LocalDateTime? = null,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,
) {
    constructor() : this(
        id = "",
        userId = "",
        sessionId = "",
        deviceType = null,
        ipAddress = null,
        userAgent = null,
        connectedAt = LocalDateTime.now(),
        disconnectedAt = null,
        isActive = true,
    )
}
