package com.adapters.web.spring.router

import com.eventhub.services.eventstore.AddAllEventsService
import com.eventhub.services.eventstore.AddEvent
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddAllEventsHandler(
    private val addAllEventsService: AddAllEventsService,
) {
    suspend fun addAll(serverRequest: ServerRequest): ServerResponse {
        val events = serverRequest.awaitBody<List<AddEvent>>()
        addAllEventsService.addAll(addEvents = events)
        return ServerResponse.accepted().buildAndAwait()
    }
}
