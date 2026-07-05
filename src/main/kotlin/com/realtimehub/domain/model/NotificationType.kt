package com.realtimehub.domain.model

enum class NotificationType {
    NEW_MESSAGE,
    MENTION,
    CHAT_INVITE,
    SYSTEM,
}

enum class NotificationStatus {
    PENDING,
    DELIVERED,
    READ,
}
