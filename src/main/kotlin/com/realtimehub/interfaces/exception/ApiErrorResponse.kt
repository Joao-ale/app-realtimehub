package com.realtimehub.interfaces.exception

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * Standard API error response format.
 */
@Schema(description = "Standard error response")
data class ApiErrorResponse(
    @field:Schema(description = "HTTP status code")
    @JsonProperty("status")
    val status: Int,

    @field:Schema(description = "Error message")
    @JsonProperty("message")
    val message: String,

    @field:Schema(description = "Error code for client handling")
    @JsonProperty("code")
    val code: String,

    @field:Schema(description = "Error details/validation errors", nullable = true)
    @JsonProperty("details")
    val details: Map<String, String>? = null,

    @field:Schema(description = "Request path")
    @JsonProperty("path")
    val path: String,

    @field:Schema(description = "Timestamp of error")
    @JsonProperty("timestamp")
    val timestamp: LocalDateTime,
)
