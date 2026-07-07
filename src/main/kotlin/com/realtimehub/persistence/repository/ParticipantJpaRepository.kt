package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.ParticipantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for Participant entity.
 */
@Repository
interface ParticipantJpaRepository : JpaRepository<ParticipantEntity, String> {
    fun findByChatIdAndIsActiveTrue(chatId: String): List<ParticipantEntity>
    fun findByUserIdAndIsActiveTrue(userId: String): List<ParticipantEntity>
    fun findByChatIdAndUserId(chatId: String, userId: String): ParticipantEntity?
    fun existsByChatIdAndUserId(chatId: String, userId: String): Boolean
}
