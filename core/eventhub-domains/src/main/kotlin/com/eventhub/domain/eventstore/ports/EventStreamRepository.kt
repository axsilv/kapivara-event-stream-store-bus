package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.EventMessage
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId

interface EventStreamRepository {
    suspend fun store(eventMessage: EventMessage)

    suspend fun fetch(eventStreamId: EventStreamId): EventStream?
}
