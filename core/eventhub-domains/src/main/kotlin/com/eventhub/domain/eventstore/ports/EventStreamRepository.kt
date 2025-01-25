package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId

interface EventStreamRepository {
    suspend fun store(
        eventStreamId: EventStreamId,
        ownerId: OwnerId,
    )

    suspend fun fetch(eventStreamId: EventStreamId): EventStream

    suspend fun exists(eventStreamId: EventStreamId): Boolean
}
