package com.adapters.web.spring.router

import com.eventhub.services.eventstore.AddEvent
import com.eventhub.services.eventstore.AddEventService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class AddEventHandler(
    private val addEventService: AddEventService,
) {
    suspend fun add(serverRequest: ServerRequest): ServerResponse {
        val event = serverRequest.awaitBody<AddEvent>()
        addEventService.add(event)
        return ServerResponse.accepted().buildAndAwait()
    }
}
