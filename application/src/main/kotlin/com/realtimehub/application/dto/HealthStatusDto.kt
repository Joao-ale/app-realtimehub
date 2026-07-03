package com.realtimehub.application.dto

data class HealthStatusDto(
    val status: String,
    val version: String,
    val modules: List<String>,
)
