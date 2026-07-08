package com.realtimehub.domain.message.event

import com.realtimehub.shared.domain.AbstractDomainEvent
import com.realtimehub.shared.utils.DateTimeUtils
import java.time.LocalDateTime
import java.util.UUID

data class MessageDeletedEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val aggregateId: String,
    override val occurredAt: LocalDateTime = DateTimeUtils.now(),
    override val version: Int = 1,
    val chatId: String,
    val senderId: String,
) : AbstractDomainEvent(eventId, aggregateId, occurredAt, version)
