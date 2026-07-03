package com.realtimehub.api.config

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.lang.management.ManagementFactory
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

@Component
class MetricsConfig(
    private val meterRegistry: MeterRegistry,
) {
    private val connectedUsers = AtomicInteger(0)
    private val websocketSessions = AtomicInteger(0)
    private val reconnections = AtomicLong(0)
    private val errorsPerMinute = AtomicLong(0)

    @PostConstruct
    fun registerMetrics() {
        Gauge.builder("realtimehub.users.connected") { connectedUsers.get().toDouble() }
            .description("Number of currently connected users")
            .register(meterRegistry)

        Gauge.builder("realtimehub.websocket.sessions") { websocketSessions.get().toDouble() }
            .description("Active WebSocket sessions")
            .register(meterRegistry)

        Gauge.builder("realtimehub.reconnections.total") { reconnections.get().toDouble() }
            .description("Total WebSocket reconnections")
            .register(meterRegistry)

        Gauge.builder("realtimehub.errors.per_minute") { errorsPerMinute.get().toDouble() }
            .description("Errors per minute")
            .register(meterRegistry)

        Gauge.builder("realtimehub.jvm.memory.used") {
            val bean = ManagementFactory.getMemoryMXBean()
            bean.heapMemoryUsage.used.toDouble()
        }.description("JVM heap memory used in bytes")
            .register(meterRegistry)

        Gauge.builder("realtimehub.jvm.cpu.load") {
            ManagementFactory.getOperatingSystemMXBean().systemLoadAverage.coerceAtLeast(0.0)
        }.description("System load average")
            .register(meterRegistry)
    }

    fun incrementConnectedUsers() = connectedUsers.incrementAndGet()
    fun decrementConnectedUsers() = connectedUsers.updateAndGet { maxOf(0, it - 1) }
    fun incrementWebsocketSessions() = websocketSessions.incrementAndGet()
    fun decrementWebsocketSessions() = websocketSessions.updateAndGet { maxOf(0, it - 1) }
    fun incrementReconnections() = reconnections.incrementAndGet()
    fun incrementErrors() = errorsPerMinute.incrementAndGet()
}
