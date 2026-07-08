package com.realtimehub.domain.event

import java.time.LocalDateTime

interface DomainEvent {
    val eventId: String
    val occurredAt: LocalDateTime
}
