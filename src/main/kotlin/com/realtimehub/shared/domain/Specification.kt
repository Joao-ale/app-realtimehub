package com.realtimehub.shared.domain

/**
 * Base interface for specifications used in domain queries.
 * Implements the Specification pattern for building complex query logic.
 */
interface Specification<T> {
    fun isSatisfiedBy(candidate: T): Boolean
}
