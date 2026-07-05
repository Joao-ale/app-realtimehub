package com.realtimehub.controller

import com.realtimehub.application.dto.HealthStatusDto
import com.realtimehub.application.usecase.GetHealthStatusService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Health check endpoints")
class HealthController(
	private val getHealthStatusService: GetHealthStatusService,
) {
    @GetMapping
    @Operation(summary = "Application health status")
    fun getHealth(): HealthStatusDto = getHealthStatusService.execute()
}
