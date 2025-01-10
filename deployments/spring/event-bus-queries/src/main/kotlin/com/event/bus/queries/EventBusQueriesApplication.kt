package com.event.bus.queries

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EventBusQueriesApplication

fun main(args: Array<String>) {
    runApplication<EventBusQueriesApplication>(*args)
}
