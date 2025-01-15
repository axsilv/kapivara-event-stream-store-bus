package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.DATABASE
import io.r2dbc.spi.ConnectionFactoryOptions.DRIVER
import io.r2dbc.spi.ConnectionFactoryOptions.HOST
import io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD
import io.r2dbc.spi.ConnectionFactoryOptions.PORT
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.context.annotation.Bean
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@Bean
suspend fun r2dbcPostgresqlConnection(r2dbcEventStoreProperties: R2dbcEventStoreProperties): Connection {
    val options =
        ConnectionFactoryOptions
            .builder()
            .option(DRIVER, "postgresql")
            .option(HOST, r2dbcEventStoreProperties.host)
            .option(PORT, r2dbcEventStoreProperties.port)
            .option(USER, r2dbcEventStoreProperties.user)
            .option(PASSWORD, r2dbcEventStoreProperties.password)
            .option(DATABASE, r2dbcEventStoreProperties.database)
            .build()

    val factory = ConnectionFactories.get(options)
    val configuration =
        ConnectionPoolConfiguration
            .builder(factory)
            .maxIdleTime(30.minutes.toJavaDuration())
            .maxSize(20)
            .build()

    return ConnectionPool(configuration).create().awaitSingle()
}
