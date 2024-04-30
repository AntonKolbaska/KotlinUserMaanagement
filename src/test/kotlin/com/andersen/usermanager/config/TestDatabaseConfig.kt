package com.andersen.usermanager.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@TestConfiguration
class TestDatabaseConfig {
    @Bean
    @Primary
    fun dataSource(): DataSource {
        val jdbcUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL;DATABASE_TO_UPPER=false"
        return DataSourceBuilder
            .create()
            .url(jdbcUrl)
            .username("admin")
            .password("admin")
            .driverClassName("org.h2.Driver")
            .build()
    }
}