package com.eventhub.ports.eventstore

import java.util.UUID

interface EventStoreRepository {
    suspend fun add(eventStore: EventStore)

    suspend fun get(eventId: UUID): EventStore?

    suspend fun getStream(eventStreamId: UUID): List<EventStore>
}
