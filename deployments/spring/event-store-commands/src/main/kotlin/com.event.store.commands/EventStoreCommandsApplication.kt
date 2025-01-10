package com.event.store.commands

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EventStoreCommandsApplication

fun main(args: Array<String>) {
    runApplication<EventStoreCommandsApplication>(*args)
}
