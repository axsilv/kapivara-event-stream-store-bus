package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.toEventStreamId
import com.eventhub.domain.eventstore.toEventStreamQueryResult
import com.eventhub.ports.eventstore.EventStoreRepository
import com.eventhub.ports.eventstore.EventStreamQueryResult
import com.eventhub.ports.eventstore.EventStreamQueryService
import java.util.UUID

class GetEventStreamService(
    private val eventStoreRepository: EventStoreRepository,
) : EventStreamQueryService {
    override suspend fun get(eventStreamId: UUID): EventStreamQueryResult =
        eventStreamId
            .toEventStreamId()
            .get(
                eventStoreRepository = eventStoreRepository,
            ).toEventStreamQueryResult()
}
