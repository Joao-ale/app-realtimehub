package com.realtimehub.infrastructure.repository

import com.realtimehub.domain.notification.entity.Notification
import com.realtimehub.domain.notification.repository.NotificationRepository
import com.realtimehub.persistence.converter.NotificationConverter
import com.realtimehub.persistence.repository.NotificationJpaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

/**
 * Implementation of NotificationRepository domain interface.
 * Bridges domain logic with JPA persistence layer.
 */
@Component
class NotificationRepositoryImpl(
    private val notificationJpaRepository: NotificationJpaRepository,
    private val notificationConverter: NotificationConverter,
) : NotificationRepository {

    override suspend fun save(aggregate: Notification): Notification =
        withContext(Dispatchers.IO) {
            val entity = notificationConverter.toEntity(aggregate)
            val savedEntity = notificationJpaRepository.save(entity)
            notificationConverter.toDomain(savedEntity)
        }

    override suspend fun findById(id: String): Notification? = withContext(Dispatchers.IO) {
        val entity = notificationJpaRepository.findById(id).orElse(null)
            ?: return@withContext null
        notificationConverter.toDomain(entity)
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext if (notificationJpaRepository.existsById(id)) {
            notificationJpaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    override suspend fun findByUserId(userId: String): List<Notification> =
        withContext(Dispatchers.IO) {
            notificationJpaRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .map { notificationConverter.toDomain(it) }
        }

    override suspend fun findUnreadByUserId(userId: String): List<Notification> =
        withContext(Dispatchers.IO) {
            notificationJpaRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId)
                .map { notificationConverter.toDomain(it) }
        }
}
