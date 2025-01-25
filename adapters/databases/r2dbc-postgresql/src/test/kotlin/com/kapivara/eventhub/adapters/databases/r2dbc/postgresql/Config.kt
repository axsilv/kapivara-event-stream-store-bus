package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient

object Config {
    fun databaseClient(): DatabaseClient {
        val connectionFactory =
            ConnectionFactories.get(
                ConnectionFactoryOptions
                    .builder()
                    .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                    .option(ConnectionFactoryOptions.HOST, "localhost")
                    .option(ConnectionFactoryOptions.PORT, 5432)
                    .option(ConnectionFactoryOptions.USER, "eventstore")
                    .option(ConnectionFactoryOptions.PASSWORD, "eventstore")
                    .option(ConnectionFactoryOptions.DATABASE, "eventstore")
                    .build(),
            )

        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))

        return DatabaseClient.create(connectionFactory)
    }
}
