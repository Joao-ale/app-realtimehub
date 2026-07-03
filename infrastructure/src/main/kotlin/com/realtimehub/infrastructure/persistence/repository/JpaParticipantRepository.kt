package com.realtimehub.infrastructure.persistence.repository

import com.realtimehub.infrastructure.persistence.entity.ParticipantEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaParticipantRepository : JpaRepository<ParticipantEntity, UUID> {
    fun findByChatId(chatId: UUID): List<ParticipantEntity>
    fun findByChatIdAndUserId(chatId: UUID, userId: UUID): ParticipantEntity?
    fun deleteByChatIdAndUserId(chatId: UUID, userId: UUID)
    fun existsByChatIdAndUserId(chatId: UUID, userId: UUID): Boolean
}
