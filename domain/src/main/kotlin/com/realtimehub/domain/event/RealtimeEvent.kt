package com.realtimehub.domain.event

import java.time.Instant
import java.util.UUID

enum class RealtimeEventType {
    CONNECT,
    DISCONNECT,
    JOIN_ROOM,
    LEAVE_ROOM,
    SEND_MESSAGE,
    MESSAGE_RECEIVED,
    MESSAGE_EDITED,
    MESSAGE_DELETED,
    USER_TYPING,
    USER_STOPPED_TYPING,
    MESSAGE_READ,
    MESSAGE_DELIVERED,
    USER_ONLINE,
    USER_OFFLINE,
    NEW_NOTIFICATION,
}

data class RealtimeEvent(
    override val eventId: UUID = UUID.randomUUID(),
    val type: RealtimeEventType,
    val payload: Map<String, Any?>,
    override val occurredAt: Instant = Instant.now(),
) : DomainEvent
