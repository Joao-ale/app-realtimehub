package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for Chat entity.
 */
@Repository
interface ChatJpaRepository : JpaRepository<ChatEntity, String> {
    fun findByIdAndIsActiveTrue(id: String): ChatEntity?
    fun findAllByCreatorIdAndIsActiveTrue(creatorId: String): List<ChatEntity>
    fun findAllByIsActiveTrue(): List<ChatEntity>
}
