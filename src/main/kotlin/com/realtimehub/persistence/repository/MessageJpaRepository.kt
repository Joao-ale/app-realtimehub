package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.MessageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for Message entity.
 */
@Repository
interface MessageJpaRepository : JpaRepository<MessageEntity, String> {
    fun findByChatIdAndIsDeletedFalse(chatId: String, pageable: Pageable): Page<MessageEntity>
    fun findBySenderIdAndIsDeletedFalse(senderId: String): List<MessageEntity>
    fun findByReplyToIdAndIsDeletedFalse(replyToId: String): List<MessageEntity>
    fun findByChatId(chatId: String): List<MessageEntity>
}
