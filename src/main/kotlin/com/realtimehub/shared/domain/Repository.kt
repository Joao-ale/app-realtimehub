package com.realtimehub.shared.domain

/**
 * Base repository interface for all domain repositories.
 * This is the interface contract for persistence layer implementations.
 */
interface Repository<T : AggregateRoot<String>> {
    suspend fun save(aggregate: T): T
    suspend fun findById(id: String): T?
    suspend fun delete(id: String): Boolean
}
