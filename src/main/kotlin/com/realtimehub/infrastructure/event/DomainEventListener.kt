package com.realtimehub.infrastructure.event

import com.realtimehub.shared.domain.DomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class DomainEventListener {
    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    @Async
    fun handleDomainEvent(event: DomainEvent) {
        logger.info(
            "Domain event occurred: {} for aggregate {}",
            event::class.simpleName,
            event.aggregateId,
        )
    }
}
