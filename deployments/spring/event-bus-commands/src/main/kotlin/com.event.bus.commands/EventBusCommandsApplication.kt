package com.event.bus.commands

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EventBusCommandsApplication

fun main(args: Array<String>) {
    runApplication<EventBusCommandsApplication>(*args)
}
