package com.kapivara.adapters.web.eventstore.queries.spring.router

import com.eventhub.spring.services.configurations.GetEventSpringService
import java.util.UUID.fromString
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

class GetEventHandler(
    private val service: GetEventSpringService,
) {
    suspend fun get(request: ServerRequest): ServerResponse {
        val eventId = fromString(request.pathVariable("event_id"))
        val ownerId = fromString(request.pathVariable("owner_id"))

        service
            .get(
                eventId = eventId,
                ownerId = ownerId,
            )?.let {
                return ServerResponse.ok().bodyValueAndAwait(it)
            }

        return ServerResponse.notFound().buildAndAwait()
    }
}
