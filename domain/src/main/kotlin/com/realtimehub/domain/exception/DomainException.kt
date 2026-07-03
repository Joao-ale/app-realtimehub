package com.realtimehub.domain.exception

open class DomainException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)

class EntityNotFoundException(
    entity: String,
    id: Any,
) : DomainException("$entity not found with id: $id")

class BusinessRuleException(
    message: String,
) : DomainException(message)

class UnauthorizedAccessException(
    message: String = "Unauthorized access",
) : DomainException(message)

class DuplicateEntityException(
    message: String,
) : DomainException(message)
