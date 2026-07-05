package com.realtimehub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.realtimehub"])
class RealtimeHubApplication

fun main(args: Array<String>) {
    runApplication<RealtimeHubApplication>(*args)
}
