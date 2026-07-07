package com.realtimehub.persistence.repository

import com.realtimehub.persistence.entity.NotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for Notification entity.
 */
@Repository
interface NotificationJpaRepository : JpaRepository<NotificationEntity, String> {
    fun findByUserIdOrderByCreatedAtDesc(userId: String): List<NotificationEntity>
    fun findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId: String): List<NotificationEntity>
    fun countByUserIdAndIsReadFalse(userId: String): Long
}
