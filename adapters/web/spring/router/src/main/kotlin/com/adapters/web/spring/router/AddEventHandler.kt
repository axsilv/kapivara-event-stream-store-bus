package com.adapters.web.spring.router

import com.eventhub.domain.eventstore.Event
import com.eventhub.services.eventstore.AddEventService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddEventHandler(
    private val addEventService: AddEventService,
) {
    suspend fun add(serverRequest: ServerRequest): ServerResponse {
        val event = serverRequest.awaitBody<Event>()
        addEventService.add(event)
        return ServerResponse.accepted().buildAndAwait()
    }
}
