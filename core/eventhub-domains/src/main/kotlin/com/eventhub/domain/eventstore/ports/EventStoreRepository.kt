package com.eventhub.domain.eventstore.ports

import java.util.UUID

interface EventStoreRepository {
    suspend fun add(eventStore: EventStore)

    suspend fun get(
        eventId: UUID,
        ownerId: UUID,
    ): EventStore?

    suspend fun getStream(eventStreamId: UUID): List<EventStore>
}
