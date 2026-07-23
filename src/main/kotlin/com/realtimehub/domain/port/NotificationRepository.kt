package com.realtimehub.domain.port

import com.realtimehub.domain.notification.entity.Notification

interface NotificationRepository {
    suspend fun save(notification: Notification): Notification
    suspend fun findById(id: String): Notification?
    suspend fun delete(id: String): Boolean
    suspend fun findByUserId(userId: String): List<Notification>
    suspend fun findUnreadByUserId(userId: String): List<Notification>
}
