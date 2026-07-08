package com.realtimehub.domain.port

import com.realtimehub.domain.notification.entity.Notification
import com.realtimehub.domain.notification.entity.NotificationStatus


interface NotificationRepository {
    fun save(notification: Notification): Notification
    fun findById(id: String): Notification?
    fun findByUserIdAndStatus(userId: String, status: NotificationStatus): List<Notification>
    fun findPendingByUserId(userId: String): List<Notification>

    fun findByUserId(userId: String): List<Notification>
    fun findUnreadByUserId(userId: String): List<Notification>
}
