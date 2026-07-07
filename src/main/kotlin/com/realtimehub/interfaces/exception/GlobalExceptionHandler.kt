package com.realtimehub.interfaces.exception

import com.realtimehub.shared.domain.DomainException
import com.realtimehub.shared.utils.DateTimeUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Global exception handler for REST API.
 * Centralizes error handling and converts exceptions to API error responses.
 */
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Handle domain exceptions.
     */
    @ExceptionHandler(DomainException::class)
    fun handleDomainException(
        ex: DomainException,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        logger.warn("Domain exception: code={}, message={}", ex.code, ex.message)
        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = ex.message ?: "Domain error occurred",
                code = ex.code,
                path = request.getDescription(false).removePrefix("uri="),
                timestamp = DateTimeUtils.now(),
            ),
        )
    }

    /**
     * Handle validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        logger.warn("Validation error: {}", ex.message)
        val errors = ex.bindingResult.fieldErrors
            .associateBy({ it.field }, { it.defaultMessage ?: "Invalid value" })

        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = "Validation failed",
                code = "VALIDATION_ERROR",
                details = errors,
                path = request.getDescription(false).removePrefix("uri="),
                timestamp = DateTimeUtils.now(),
            ),
        )
    }

    /**
     * Handle illegal argument exceptions.
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        logger.warn("Illegal argument: {}", ex.message)
        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = ex.message ?: "Invalid argument",
                code = "INVALID_ARGUMENT",
                path = request.getDescription(false).removePrefix("uri="),
                timestamp = DateTimeUtils.now(),
            ),
        )
    }

    /**
     * Handle all other exceptions.
     */
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(
        ex: Exception,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        logger.error("Unexpected error: {}", ex.message, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiErrorResponse(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "An unexpected error occurred",
                code = "INTERNAL_SERVER_ERROR",
                path = request.getDescription(false).removePrefix("uri="),
                timestamp = DateTimeUtils.now(),
            ),
        )
    }
}
