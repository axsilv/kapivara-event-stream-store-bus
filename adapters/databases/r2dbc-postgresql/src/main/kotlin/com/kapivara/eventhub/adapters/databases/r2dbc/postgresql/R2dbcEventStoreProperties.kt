package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import org.springframework.context.annotation.Configuration

@Configuration
data class R2dbcEventStoreProperties(
    val host: String,
    val port: Int,
    val user: String,
    val password: String,
    val database: String,
)
