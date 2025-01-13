package com.eventstore.queries.router

import com.eventhub.ports.eventstore.EventQueryService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.UUID

class GetEventHandler(
    private val eventQueryService: EventQueryService,
) {
    suspend fun get(request: ServerRequest): ServerResponse {
        val eventId = UUID.fromString(request.pathVariable("event_id"))
        val event = eventQueryService.get(eventId = eventId)
        return ServerResponse.ok().bodyValueAndAwait(event)
    }
}
