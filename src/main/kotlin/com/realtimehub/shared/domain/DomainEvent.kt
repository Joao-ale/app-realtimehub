package com.realtimehub.shared.domain

import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Base interface for all domain events.
 * Domain events represent something significant that happened in the domain.
 */
interface DomainEvent {
    val eventId: String
    val aggregateId: String
    val occurredAt: LocalDateTime
    val version: Int
}

/**
 * Abstract base class for domain events with common fields.
 */
abstract class AbstractDomainEvent(
    override val eventId: String = java.util.UUID.randomUUID().toString(),
    override val aggregateId: String,
    override val occurredAt: LocalDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
    override val version: Int = 1,
) : DomainEvent
