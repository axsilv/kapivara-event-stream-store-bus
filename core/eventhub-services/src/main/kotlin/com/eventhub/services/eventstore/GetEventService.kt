package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.toEventId
import com.eventhub.ports.eventstore.EventStoreRepository
import java.util.UUID

class GetEventService(
    private val eventStoreRepository: EventStoreRepository,
) {
    suspend fun get(eventId: UUID): Event =
        eventId
            .toEventId()
            .get(
                eventStoreRepository = eventStoreRepository,
            )
}
