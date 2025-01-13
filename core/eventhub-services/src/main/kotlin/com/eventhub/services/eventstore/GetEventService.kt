package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.toEventId
import com.eventhub.domain.eventstore.toEventQueryResult
import com.eventhub.ports.eventstore.EventQueryResult
import com.eventhub.ports.eventstore.EventQueryService
import com.eventhub.ports.eventstore.EventStoreRepository
import java.util.UUID

class GetEventService(
    private val eventStoreRepository: EventStoreRepository,
) : EventQueryService {
    override suspend fun get(eventId: UUID): EventQueryResult =
        eventId
            .toEventId()
            .get(
                eventStoreRepository = eventStoreRepository,
            ).toEventQueryResult()
}
