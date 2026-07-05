package com.realtimehub.domain.port

import com.realtimehub.domain.model.Participant
import java.util.UUID

interface ParticipantRepository {
    fun save(participant: Participant): Participant
    fun saveAll(participants: List<Participant>): List<Participant>
    fun findByChatId(chatId: UUID): List<Participant>
    fun findByChatIdAndUserId(chatId: UUID, userId: UUID): Participant?
    fun deleteByChatIdAndUserId(chatId: UUID, userId: UUID)
    fun existsByChatIdAndUserId(chatId: UUID, userId: UUID): Boolean
}
