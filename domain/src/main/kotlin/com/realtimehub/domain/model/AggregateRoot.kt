package com.realtimehub.domain.model

import java.time.Instant
import java.util.UUID

abstract class AggregateRoot(
    open val id: UUID,
    open val createdAt: Instant,
    open val updatedAt: Instant,
)
