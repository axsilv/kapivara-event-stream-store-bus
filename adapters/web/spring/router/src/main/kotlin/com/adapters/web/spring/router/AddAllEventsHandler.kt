package com.adapters.web.spring.router

import com.eventhub.domain.eventstore.Event
import com.eventhub.services.eventstore.AddAllEventsService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddAllEventsHandler(
    private val addAllEventsService: AddAllEventsService,
) {
    suspend fun addAll(serverRequest: ServerRequest): ServerResponse {
        val events = serverRequest.awaitBody<List<Event>>()
        addAllEventsService.addAll(events = events)
        return ServerResponse.accepted().buildAndAwait()
    }
}
