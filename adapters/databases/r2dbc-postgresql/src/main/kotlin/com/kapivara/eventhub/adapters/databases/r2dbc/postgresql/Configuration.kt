package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import kotlinx.coroutines.reactor.awaitSingle
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

suspend fun connection(): Connection {
    val options =
        ConnectionFactoryOptions
            .builder()
            .option(ConnectionFactoryOptions.DRIVER, "postgresql")
            .option(ConnectionFactoryOptions.HOST, "<host>")
            .option(ConnectionFactoryOptions.PORT, 5432)
            .option(ConnectionFactoryOptions.USER, "<your-username>")
            .option(ConnectionFactoryOptions.PASSWORD, "<your-password>")
            .option(ConnectionFactoryOptions.DATABASE, "<database>")
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
