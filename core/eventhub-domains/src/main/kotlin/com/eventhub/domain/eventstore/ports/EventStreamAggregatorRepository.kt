package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.EventStreamAggregator
import com.eventhub.domain.eventstore.EventStreamAggregator.EventStreamAggregatorId

interface EventStreamAggregatorRepository {
    suspend fun collect(eventStreamAggregatorId: EventStreamAggregatorId)

    suspend fun fetchFinishedStreams(): List<EventStreamAggregatorId>

    suspend fun store(eventStreamAggregator: EventStreamAggregator)
}
