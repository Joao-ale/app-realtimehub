package com.realtimehub.domain.event

import java.time.Instant
import java.util.UUID

interface DomainEvent {
    val eventId: UUID
    val occurredAt: Instant
}
