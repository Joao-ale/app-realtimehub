package com.realtimehub.domain.port

import com.realtimehub.domain.event.RealtimeEvent

interface EventPublisher {
    fun publish(event: RealtimeEvent)
    fun publishToRoom(roomId: String, event: RealtimeEvent)
}
