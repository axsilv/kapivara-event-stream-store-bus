package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.toEventStreamId
import com.eventhub.services.eventstore.EventStreamQueryResult
import java.util.UUID

open class GetEventStreamService(
    private val eventStoreRepository: EventStoreRepository,
) {
    open suspend fun get(eventStreamId: UUID): EventStreamQueryResult =
        eventStreamId
            .toEventStreamId()
            .get(
                eventStoreRepository = eventStoreRepository,
            ).toEventStreamQueryResult()
}
