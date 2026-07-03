package com.realtimehub.infrastructure.security

import com.realtimehub.domain.port.MessageSanitizer
import org.springframework.stereotype.Component

@Component
class HtmlMessageSanitizer : MessageSanitizer {
    private val dangerousPattern = Regex("[<>]")

    override fun sanitize(content: String): String =
        content.trim()
            .replace(dangerousPattern, "")
            .take(MAX_LENGTH)

    companion object {
        const val MAX_LENGTH = 10_000
    }
}
