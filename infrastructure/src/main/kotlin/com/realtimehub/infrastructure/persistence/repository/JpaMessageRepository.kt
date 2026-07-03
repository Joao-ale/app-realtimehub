package com.realtimehub.infrastructure.persistence.repository

import com.realtimehub.infrastructure.persistence.entity.MessageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaMessageRepository : JpaRepository<MessageEntity, UUID> {
    fun findByChatIdOrderByCreatedAtDesc(chatId: UUID, pageable: Pageable): Page<MessageEntity>
}
