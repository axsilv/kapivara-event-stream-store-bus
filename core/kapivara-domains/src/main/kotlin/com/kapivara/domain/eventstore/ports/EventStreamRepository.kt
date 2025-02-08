package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.EventMessage
import com.kapivara.domain.eventstore.EventStream
import com.kapivara.domain.eventstore.EventStream.EventStreamId

interface EventStreamRepository {
    suspend fun store(eventMessage: EventMessage)

    suspend fun fetch(
        eventStreamId: EventStreamId,
        useCache: Boolean,
    ): EventStream?
}
