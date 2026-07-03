package com.realtimehub.infrastructure.persistence.repository

import com.realtimehub.infrastructure.persistence.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface JpaChatRepository : JpaRepository<ChatEntity, UUID> {
    @Query(
        """
        SELECT c FROM ChatEntity c
        JOIN ParticipantEntity p ON p.chatId = c.id
        WHERE p.userId = :userId
        ORDER BY c.updatedAt DESC
        """,
    )
    fun findByUserId(userId: UUID): List<ChatEntity>

    @Query(
        """
        SELECT c FROM ChatEntity c
        JOIN ParticipantEntity p1 ON p1.chatId = c.id AND p1.userId = :userId1
        JOIN ParticipantEntity p2 ON p2.chatId = c.id AND p2.userId = :userId2
        WHERE c.type = 'PRIVATE'
        """,
    )
    fun findPrivateChatBetween(userId1: UUID, userId2: UUID): ChatEntity?
}
