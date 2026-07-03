package com.realtimehub.domain.port

interface MessageSanitizer {
    fun sanitize(content: String): String
}
