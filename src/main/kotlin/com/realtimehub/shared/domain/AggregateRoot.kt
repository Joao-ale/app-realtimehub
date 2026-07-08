package com.realtimehub.shared.domain

/**
 * Base class for all aggregate roots.
 * An aggregate root is the main entity that manages the consistency of a set of entities and value objects.
 */
abstract class AggregateRoot<ID : Any>(
    open val id: ID,
) {
    private val domainEvents = mutableListOf<DomainEvent>()

    /**
     * Register a domain event to be published.
     */
    protected fun registerEvent(event: DomainEvent) {
        domainEvents.add(event)
    }

    /**
     * Get all registered domain events.
     */
    fun getDomainEvents(): List<DomainEvent> = domainEvents.toList()

    /**
     * Clear all domain events after publishing.
     */
    fun clearDomainEvents() {
        domainEvents.clear()
    }

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}
