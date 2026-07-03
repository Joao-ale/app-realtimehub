package com.realtimehub.application.usecase

import com.realtimehub.application.dto.HealthStatusDto

class GetHealthStatusUseCase(
    private val version: String,
) {
    fun execute(): HealthStatusDto =
        HealthStatusDto(
            status = "UP",
            version = version,
            modules = listOf("domain", "application", "infrastructure", "api"),
        )
}
