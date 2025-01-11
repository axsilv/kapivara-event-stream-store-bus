package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.toEventStreamId
import com.eventhub.ports.eventstore.EventStoreRepository
import java.util.UUID

class GetEventStreamService(
    private val eventStoreRepository: EventStoreRepository,
) {
    suspend fun get(eventStreamId: UUID): EventStream =
        eventStreamId
            .toEventStreamId()
            .get(
                eventStoreRepository = eventStoreRepository,
            )
}
