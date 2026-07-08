package com.realtimehub.domain.chat.event

import com.realtimehub.shared.domain.AbstractDomainEvent
import com.realtimehub.shared.utils.DateTimeUtils
import java.time.LocalDateTime
import java.util.UUID

data class ChatCreatedEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val aggregateId: String,
    override val occurredAt: LocalDateTime = DateTimeUtils.now(),
    override val version: Int = 1,
    val name: String?,
    val type: String,
    val creatorId: String,
) : AbstractDomainEvent(eventId, aggregateId, occurredAt, version)
