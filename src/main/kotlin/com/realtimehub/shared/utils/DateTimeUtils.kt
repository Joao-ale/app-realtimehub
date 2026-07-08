package com.realtimehub.shared.utils

import java.time.LocalDateTime
import java.time.ZoneId


object DateTimeUtils {
    private val BRASIL_ZONE = ZoneId.of("America/Sao_Paulo")

    /**
     * Get current date and time in Brasilia timezone.
     */
    fun now(): LocalDateTime = LocalDateTime.now(BRASIL_ZONE)

    /**
     * Get Brasilia timezone.
     */
    fun getBrasilZone(): ZoneId = BRASIL_ZONE
}
