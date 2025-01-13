package com.eventstore.queries.router

import com.eventhub.ports.eventstore.EventStreamQueryService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.UUID

class GetEventStreamHandler(
    private val eventStreamQueryService: EventStreamQueryService,
) {
    suspend fun get(serverRequest: ServerRequest): ServerResponse {
        val eventStreamId = UUID.fromString(serverRequest.pathVariable(""))
        val eventStream = eventStreamQueryService.get(eventStreamId = eventStreamId)
        return ServerResponse.ok().bodyValueAndAwait(eventStream)
    }
}
