package com.eventstore.commands.router

import com.eventhub.ports.eventstore.AddEvent
import com.eventhub.ports.eventstore.EventsCommandService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddAllEventsHandler(
    private val eventsCommandService: EventsCommandService,
) {
    suspend fun addAll(serverRequest: ServerRequest): ServerResponse {
        val events = serverRequest.awaitBody<List<AddEvent>>()
        eventsCommandService.addAll(addEvents = events)
        return ServerResponse.accepted().buildAndAwait()
    }
}
