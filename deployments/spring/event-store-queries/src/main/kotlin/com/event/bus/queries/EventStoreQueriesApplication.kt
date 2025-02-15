package com.event.bus.queries

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EventStoreQueriesApplication

fun main(args: Array<String>) {
    runApplication<EventStoreQueriesApplication>(*args)
}
