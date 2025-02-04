package com.kapivara.adapters.web.eventstore.queries.spring.router

import com.eventhub.spring.services.configurations.GetEventStreamSpringService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.UUID.fromString

class GetEventStreamHandler(
    private val service: GetEventStreamSpringService,
) {
    suspend fun get(serverRequest: ServerRequest): ServerResponse {
        val eventStreamId = fromString(serverRequest.pathVariable(""))
        val eventStream = service.get(eventStreamId = eventStreamId)
        return ServerResponse.ok().bodyValueAndAwait(eventStream)
    }
}
