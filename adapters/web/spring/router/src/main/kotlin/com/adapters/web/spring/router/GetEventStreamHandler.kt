package com.adapters.web.spring.router

import com.eventhub.services.eventstore.GetEventStreamService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.UUID

class GetEventStreamHandler(
    private val getEventStreamService: GetEventStreamService,
) {
    suspend fun get(serverRequest: ServerRequest): ServerResponse {
        val eventStreamId = UUID.fromString(serverRequest.pathVariable(""))
        val eventStream = getEventStreamService.get(eventStreamId = eventStreamId)
        return ServerResponse.ok().bodyValueAndAwait(eventStream)
    }
}
