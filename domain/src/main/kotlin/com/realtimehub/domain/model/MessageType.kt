package com.realtimehub.domain.model

enum class MessageType {
    TEXT,
    IMAGE,
    FILE,
    AUDIO,
    SYSTEM,
}

enum class MessageStatus {
    SENT,
    DELIVERED,
    READ,
    DELETED,
}
