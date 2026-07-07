package com.realtimehub.shared.domain

/**
 * Sealed class representing domain errors.
 * These are business logic errors that can occur during domain operations.
 */
sealed class DomainError(
    open val message: String,
    open val code: String,
) {
    data class ValidationError(
        override val message: String,
        override val code: String = "VALIDATION_ERROR",
    ) : DomainError(message, code)

    data class NotFoundError(
        override val message: String,
        override val code: String = "NOT_FOUND_ERROR",
    ) : DomainError(message, code)

    data class DuplicateError(
        override val message: String,
        override val code: String = "DUPLICATE_ERROR",
    ) : DomainError(message, code)

    data class UnauthorizedError(
        override val message: String,
        override val code: String = "UNAUTHORIZED_ERROR",
    ) : DomainError(message, code)

    data class ForbiddenError(
        override val message: String,
        override val code: String = "FORBIDDEN_ERROR",
    ) : DomainError(message, code)

    data class BusinessRuleError(
        override val message: String,
        override val code: String = "BUSINESS_RULE_ERROR",
    ) : DomainError(message, code)

    fun toException(): DomainException = DomainException(message, code)
}

class DomainException(
    message: String,
    val code: String,
) : Exception(message)
