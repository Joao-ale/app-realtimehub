package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.WebSocketSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for WebSocket Session entity.
 */
@Repository
interface WebSocketSessionJpaRepository : JpaRepository<WebSocketSessionEntity, String> {
    fun findByUserIdAndIsActiveTrue(userId: String): List<WebSocketSessionEntity>
    fun findBySessionId(sessionId: String): WebSocketSessionEntity?
    fun findAllByIsActiveTrue(): List<WebSocketSessionEntity>
    fun countByUserIdAndIsActiveTrue(userId: String): Long
}
