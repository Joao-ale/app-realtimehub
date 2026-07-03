package com.realtimehub.infrastructure.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.realtimehub.domain.event.RealtimeEvent
import com.realtimehub.domain.port.EventPublisher
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisEventPublisher(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper,
) : EventPublisher {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val REALTIME_CHANNEL = "realtime:events"
        const val ROOM_CHANNEL_PREFIX = "realtime:room:"
    }

    override fun publish(event: RealtimeEvent) {
        val payload = objectMapper.writeValueAsString(event)
        redisTemplate.convertAndSend(REALTIME_CHANNEL, payload)
        log.debug("Published event {} to channel {}", event.type, REALTIME_CHANNEL)
    }

    override fun publishToRoom(roomId: String, event: RealtimeEvent) {
        val channel = "$ROOM_CHANNEL_PREFIX$roomId"
        val payload = objectMapper.writeValueAsString(event)
        redisTemplate.convertAndSend(channel, payload)
        log.debug("Published event {} to room {}", event.type, roomId)
    }
}
