package com.event.store.worker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EventStoreWorkerApplication

fun main(args: Array<String>) {
    runApplication<EventStoreWorkerApplication>(*args)
}
