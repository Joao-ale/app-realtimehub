package com.realtimehub.domain.port

data class PageRequest(
    val page: Int = 0,
    val size: Int = 20,
)

data class PageResult<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
)
