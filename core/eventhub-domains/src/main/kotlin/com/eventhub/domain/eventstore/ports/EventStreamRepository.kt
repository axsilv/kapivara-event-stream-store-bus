package com.eventhub.domain.eventstore.ports

import java.util.UUID

interface EventStreamRepository {
    suspend fun add(
        eventStreamId: UUID,
        ownerId: UUID,
    )

    suspend fun get(eventStreamId: UUID): List<EventStore>
}
