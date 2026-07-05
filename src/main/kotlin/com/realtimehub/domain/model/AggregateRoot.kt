package com.realtimehub.domain.model

import java.time.LocalDateTime

abstract class AggregateRoot(
    open val id: String,
    open val createdAt: LocalDateTime,
    open val updatedAt: LocalDateTime,
)
