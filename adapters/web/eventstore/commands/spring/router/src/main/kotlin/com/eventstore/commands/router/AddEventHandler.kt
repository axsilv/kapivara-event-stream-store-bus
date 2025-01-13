package com.eventstore.commands.router

import com.eventhub.ports.eventstore.AddEvent
import com.eventhub.ports.eventstore.EventCommandService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddEventHandler(
    private val eventCommandService: EventCommandService,
) {
    suspend fun add(serverRequest: ServerRequest): ServerResponse {
        val event = serverRequest.awaitBody<AddEvent>()
        eventCommandService.add(event)
        return ServerResponse.accepted().buildAndAwait()
    }
}
