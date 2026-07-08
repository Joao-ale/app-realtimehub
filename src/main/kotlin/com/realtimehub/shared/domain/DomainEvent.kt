package com.realtimehub.shared.domain

import com.realtimehub.shared.utils.DateTimeUtils
import java.time.LocalDateTime


interface DomainEvent {
    val eventId: String
    val aggregateId: String
    val occurredAt: LocalDateTime
    val version: Int
}


abstract class AbstractDomainEvent(
    override val eventId: String = java.util.UUID.randomUUID().toString(),
    override val aggregateId: String,
    override val occurredAt: LocalDateTime = DateTimeUtils.now(),
    override val version: Int = 1,
) : DomainEvent
