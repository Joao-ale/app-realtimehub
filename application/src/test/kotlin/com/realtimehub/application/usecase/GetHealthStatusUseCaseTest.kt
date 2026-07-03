package com.realtimehub.application.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetHealthStatusUseCaseTest {
    private val useCase = GetHealthStatusUseCase("0.1.0-SNAPSHOT")

    @Test
    fun `should return health status with version and modules`() {
        val result = useCase.execute()

        assertEquals("UP", result.status)
        assertEquals("0.1.0-SNAPSHOT", result.version)
        assertEquals(listOf("domain", "application", "infrastructure", "api"), result.modules)
    }
}
