package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.ports.eventstore.EventStoreRepository

class GetEventStreamService(
    private val eventStoreRepository: EventStoreRepository,
) {
    suspend fun get(eventStreamId: EventStreamId): EventStream =
        eventStreamId.get(
            eventStoreRepository = eventStoreRepository,
        )
}
