package com.kapivara.adapters.web.eventstore.commands.spring.router

import com.eventhub.services.eventstore.AddEvent
import com.eventhub.spring.services.configurations.AddAllEventsSpringService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddAllEventsHandler(
    private val service: AddAllEventsSpringService,
) {
    suspend fun addAll(serverRequest: ServerRequest): ServerResponse {
        val events = serverRequest.awaitBody<List<AddEvent>>()
        service.addAll(addEvents = events)
        return ServerResponse.accepted().buildAndAwait()
    }
}
