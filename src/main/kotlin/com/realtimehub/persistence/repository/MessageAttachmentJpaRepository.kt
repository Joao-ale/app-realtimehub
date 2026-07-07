package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.MessageAttachmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for Message Attachment entity.
 */
@Repository
interface MessageAttachmentJpaRepository : JpaRepository<MessageAttachmentEntity, String> {
    fun findByMessageId(messageId: String): List<MessageAttachmentEntity>
}
