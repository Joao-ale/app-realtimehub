package com.realtimehub.domain.message.event

import com.realtimehub.shared.domain.AbstractDomainEvent
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

data class MessageEditedEvent(
    override val eventId: String = UUID.randomUUID().toString(),
    override val aggregateId: String,
    override val occurredAt: LocalDateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
    override val version: Int = 1,
    val chatId: String,
    val senderId: String,
    val previousContent: String?,
    val newContent: String,
) : AbstractDomainEvent(eventId, aggregateId, occurredAt, version)
