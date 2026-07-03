package com.realtimehub.infrastructure.security

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HtmlMessageSanitizerTest {
    private val sanitizer = HtmlMessageSanitizer()

    @Test
    fun `should remove dangerous characters`() {
        val result = sanitizer.sanitize("<script>alert('xss')</script>Hello")

        assertEquals("scriptalert('xss')/scriptHello", result)
    }

    @Test
    fun `should trim whitespace`() {
        assertEquals("hello", sanitizer.sanitize("  hello  "))
    }

    @Test
    fun `should enforce max length`() {
        val longContent = "a".repeat(HtmlMessageSanitizer.MAX_LENGTH + 100)
        assertEquals(HtmlMessageSanitizer.MAX_LENGTH, sanitizer.sanitize(longContent).length)
    }
}

class BcryptPasswordEncoderAdapterTest {
    private val encoder = BcryptPasswordEncoderAdapter()

    @Test
    fun `should encode and match password`() {
        val raw = "SecureP@ss123"
        val encoded = encoder.encode(raw)

        assertFalse(raw == encoded)
        assertTrue(encoder.matches(raw, encoded))
    }
}
