package com.realtimehub.api.config

import com.realtimehub.application.usecase.GetHealthStatusUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {
    @Bean
    fun getHealthStatusUseCase(
        @Value("\${app.version}") version: String,
    ): GetHealthStatusUseCase = GetHealthStatusUseCase(version)
}
