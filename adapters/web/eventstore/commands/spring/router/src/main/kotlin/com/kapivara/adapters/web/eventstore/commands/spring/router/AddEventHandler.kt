package com.kapivara.adapters.web.eventstore.commands.spring.router

import com.eventhub.services.eventstore.AddEvent
import com.eventhub.spring.services.configurations.AddEventSpringService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddEventHandler(
    private val service: AddEventSpringService,
) {
    suspend fun add(serverRequest: ServerRequest): ServerResponse {
        val event = serverRequest.awaitBody<AddEvent>()
        service.add(event)
        return ServerResponse.accepted().buildAndAwait()
    }
}
