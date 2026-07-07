package com.realtimehub.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * OpenAPI configuration for Swagger documentation.
 */
@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("RealtimeHub API")
                .version("1.0.0")
                .description("Real-time chat system API with WebSocket support")
                .contact(
                    Contact()
                        .name("RealtimeHub Support")
                        .email("support@realtimehub.com")
                        .url("https://realtimehub.com"),
                )
                .license(
                    License()
                        .name("MIT")
                        .url("https://opensource.org/licenses/MIT"),
                ),
        )
}
