package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.toEventId
import com.eventhub.domain.eventstore.toOwnerId
import java.util.UUID

open class GetEventService(
    private val eventStoreRepository: EventStoreRepository,
) {
    open suspend fun get(
        eventId: UUID,
        ownerId: UUID,
    ): EventQueryResult? =
        eventId
            .toEventId()
            .get(
                eventStoreRepository = eventStoreRepository,
                subscriberId = ownerId.toOwnerId(),
            )?.toEventQueryResult()
}
