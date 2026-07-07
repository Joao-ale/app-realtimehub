package com.realtimehub.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * JPA Entity for Message Attachment persistence.
 * Maps to the 'message_attachments' table in the database.
 */
@Entity
@Table(name = "message_attachments")
data class MessageAttachmentEntity(
    @Id
    @Column(name = "id", length = 36)
    val id: String = "",

    @Column(name = "message_id", length = 36, nullable = false)
    val messageId: String = "",

    @Column(name = "file_url", length = 1024, nullable = false)
    val fileUrl: String = "",

    @Column(name = "file_name", length = 255, nullable = false)
    val fileName: String = "",

    @Column(name = "file_type", length = 50, nullable = true)
    val fileType: String? = null,

    @Column(name = "file_size", nullable = true)
    val fileSize: Long? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor() : this(
        id = "",
        messageId = "",
        fileUrl = "",
        fileName = "",
        fileType = null,
        fileSize = null,
        createdAt = LocalDateTime.now(),
    )
}
