package com.realtimehub.config

import com.realtimehub.application.usecase.GetHealthStatusService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {
    @Bean
    fun getHealthStatusUseCase(
        @Value("\${app.version}") version: String,
    ): GetHealthStatusService = GetHealthStatusService(version)
}
