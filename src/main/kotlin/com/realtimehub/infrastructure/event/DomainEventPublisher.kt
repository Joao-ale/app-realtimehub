package com.realtimehub.infrastructure.event

import com.realtimehub.shared.domain.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * Domain Event Publisher.
 * Publishes domain events to Spring's event system for other components to listen.
 */
@Component
class DomainEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    /**
     * Publish a domain event.
     */
    fun publish(event: DomainEvent) {
        applicationEventPublisher.publishEvent(event)
    }

    /**
     * Publish multiple domain events.
     */
    fun publishAll(events: List<DomainEvent>) {
        events.forEach { publish(it) }
    }
}
