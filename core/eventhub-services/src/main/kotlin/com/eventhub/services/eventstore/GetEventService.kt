package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.ports.eventstore.EventStoreRepository

class GetEventService(
    private val eventStoreRepository: EventStoreRepository,
) {
    suspend fun get(eventId: EventId): Event =
        eventId.get(
            eventStoreRepository = eventStoreRepository,
        )
}
