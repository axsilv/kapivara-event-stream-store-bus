package com.event.store.worker.dlq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EventStoreWorkerDlqApplication

fun main(args: Array<String>) {
    runApplication<EventStoreWorkerDlqApplication>(*args)
}
