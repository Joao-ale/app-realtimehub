package com.realtimehub.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

data class ErrorResponse(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String? = null,
    val details: Map<String, String>? = null,
)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFound(ex: EntityNotFoundException): ResponseEntity<ErrorResponse> =
        buildResponse(HttpStatus.NOT_FOUND, ex.message ?: "Resource not found")

    @ExceptionHandler(DuplicateEntityException::class)
    fun handleDuplicate(ex: DuplicateEntityException): ResponseEntity<ErrorResponse> =
        buildResponse(HttpStatus.CONFLICT, ex.message ?: "Resource already exists")

    @ExceptionHandler(BusinessRuleException::class)
    fun handleBusinessRule(ex: BusinessRuleException): ResponseEntity<ErrorResponse> =
        buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.message ?: "Business rule violation")

    @ExceptionHandler(UnauthorizedAccessException::class)
    fun handleUnauthorized(ex: UnauthorizedAccessException): ResponseEntity<ErrorResponse> =
        buildResponse(HttpStatus.FORBIDDEN, ex.message ?: "Access denied")

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val details = ex.bindingResult.allErrors
            .filterIsInstance<FieldError>()
            .associate { it.field to (it.defaultMessage ?: "Invalid value") }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = HttpStatus.BAD_REQUEST.reasonPhrase,
                    message = "Validation failed",
                    details = details,
                ),
            )
    }

    @ExceptionHandler(DomainException::class)
    fun handleDomain(ex: DomainException): ResponseEntity<ErrorResponse> =
        buildResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Domain error")

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ErrorResponse> =
        buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred")

    private fun buildResponse(status: HttpStatus, message: String): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(status)
            .body(
                ErrorResponse(
                    status = status.value(),
                    error = status.reasonPhrase,
                    message = message,
                ),
            )
}
