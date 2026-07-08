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


@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private val loggerFactory = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(
        ex: DomainException,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        loggerFactory.warn("Domain exception: code={}, message={}", ex.code, ex.message)
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


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        loggerFactory.warn("Validation error: ${ex}")
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

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        loggerFactory.warn("Illegal argument: {}", ex)
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
        loggerFactory.error("Unexpected error: {}", ex.message, ex)
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

    @ExceptionHandler(InvalidPasswordException::class)
    fun handleInvalidPasswordException(
        ex: IllegalArgumentException,
        request: WebRequest,
    ): ResponseEntity<ApiErrorResponse> {
        loggerFactory.warn("Invalid password: {}", ex)
        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = ex.message ?: "Invalid password",
                code = "INVALID_PASSWORD",
                path = request.getDescription(false).removePrefix("uri="),
                timestamp = DateTimeUtils.now(),
            ),
        )
    }
}
