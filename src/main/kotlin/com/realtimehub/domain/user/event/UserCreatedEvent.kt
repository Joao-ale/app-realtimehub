package com.realtimehub.domain.user.event

import com.realtimehub.shared.domain.AbstractDomainEvent
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

data class UserCreatedEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val aggregateId: String,
    override val occurredAt: LocalDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
    override val version: Int = 1,
    val email: String,
    val username: String,
    val fullName: String? = null,
) : AbstractDomainEvent(eventId, aggregateId, occurredAt, version)
