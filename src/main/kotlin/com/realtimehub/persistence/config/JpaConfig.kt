package com.realtimehub.persistence.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * JPA Configuration for Spring Data repositories.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = ["com.realtimehub.persistence.repository"],
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
)
class JpaConfig
