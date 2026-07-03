package com.realtimehub.domain.port

import com.realtimehub.domain.model.Notification
import com.realtimehub.domain.model.NotificationStatus
import java.util.UUID

interface NotificationRepository {
    fun save(notification: Notification): Notification
    fun findById(id: UUID): Notification?
    fun findByUserIdAndStatus(userId: UUID, status: NotificationStatus): List<Notification>
    fun findPendingByUserId(userId: UUID): List<Notification>
}
