package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

/**
 * JPA Entity for Message Reaction persistence.
 * Maps to the 'message_reactions' table in the database.
 */
@Entity
@Table(
    name = "message_reactions",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_reactions_message_user_emoji",
            columnNames = ["message_id", "user_id", "emoji"],
        ),
    ],
)
data class MessageReactionEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "message_id", length = 36, nullable = false)
    val messageId: String = "",

    @Column(name = "user_id", length = 36, nullable = false)
    val userId: String = "",

    @Column(name = "emoji", length = 10, nullable = false)
    val emoji: String = "",

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor() : this(
        id = "",
        messageId = "",
        userId = "",
        emoji = "",
        createdAt = LocalDateTime.now(),
    )
}
