package com.kapivara.adapters.web.eventstore.queries.spring.router

import com.eventhub.spring.services.configurations.GetEventStreamSpringService
import java.util.UUID.fromString
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class GetEventStreamHandler(
    private val service: GetEventStreamSpringService,
) {
    suspend fun get(serverRequest: ServerRequest): ServerResponse {
        val eventStreamId = fromString(serverRequest.pathVariable(""))
        val eventStream = service.get(eventStreamId = eventStreamId)
        return ServerResponse.ok().bodyValueAndAwait(eventStream)
    }
}
