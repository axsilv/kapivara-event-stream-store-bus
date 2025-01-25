package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId

interface EventStoreRepository {
    suspend fun store(eventStore: EventStore)

    suspend fun fetch(
        eventId: EventId,
        ownerId: OwnerId,
    ): EventStore?
}
