package com.adapters.web.spring.router

import com.eventhub.services.eventstore.GetEventService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.UUID

class GetEventHandler(
    private val getEventService: GetEventService,
) {
    suspend fun get(request: ServerRequest): ServerResponse {
        val eventId = UUID.fromString(request.pathVariable("event_id"))
        val event = getEventService.get(eventId = eventId)
        return ServerResponse.ok().bodyValueAndAwait(event)
    }
}
