package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.MessageReactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for Message Reaction entity.
 */
@Repository
interface MessageReactionJpaRepository : JpaRepository<MessageReactionEntity, String> {
    fun findByMessageId(messageId: String): List<MessageReactionEntity>
    fun findByMessageIdAndUserId(messageId: String, userId: String): List<MessageReactionEntity>
    fun existsByMessageIdAndUserIdAndEmoji(messageId: String, userId: String, emoji: String): Boolean
    fun deleteByMessageIdAndUserIdAndEmoji(messageId: String, userId: String, emoji: String)
}
