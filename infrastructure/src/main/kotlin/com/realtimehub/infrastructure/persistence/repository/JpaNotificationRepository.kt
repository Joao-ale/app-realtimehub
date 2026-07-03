package com.realtimehub.infrastructure.persistence.repository

import com.realtimehub.infrastructure.persistence.entity.NotificationEntity
import com.realtimehub.infrastructure.persistence.entity.NotificationStatusEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaNotificationRepository : JpaRepository<NotificationEntity, UUID> {
    fun findByUserIdAndStatus(userId: UUID, status: NotificationStatusEntity): List<NotificationEntity>
    fun findByUserIdAndStatusOrderByCreatedAtDesc(
        userId: UUID,
        status: NotificationStatusEntity,
    ): List<NotificationEntity>
}
