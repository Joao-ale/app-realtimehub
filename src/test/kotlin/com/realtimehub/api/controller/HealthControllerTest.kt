package com.realtimehub.api.controller

import com.ninjasquad.springmockk.MockkBean
import com.realtimehub.application.dto.HealthStatusDto
import com.realtimehub.application.usecase.GetHealthStatusService
import com.realtimehub.controller.HealthController
import com.realtimehub.exception.GlobalExceptionHandler
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(controllers = [HealthController::class])
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler::class)
class HealthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var getHealthStatusService: GetHealthStatusService

    @Test
    fun `should return health status without authentication`() {
        every { getHealthStatusService.execute() } returns HealthStatusDto(
            status = "UP",
            version = "0.1.0-SNAPSHOT",
            modules = listOf("domain", "application", "infrastructure", "api"),
        )

        mockMvc.get("/api/v1/health") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.status") { value("UP") }
            jsonPath("$.version") { value("0.1.0-SNAPSHOT") }
        }
    }
}
