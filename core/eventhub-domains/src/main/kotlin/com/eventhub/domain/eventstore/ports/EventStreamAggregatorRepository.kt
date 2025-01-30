package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.EventAggregate
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId

interface EventStreamAggregatorRepository {
    suspend fun collect(eventStreamId: EventStreamId)

    suspend fun fetchFinishedStreams(): List<EventStreamId>

    suspend fun store(eventStream: EventStream)

    suspend fun store(eventAggregate: EventAggregate)

    suspend fun fetch(eventStreamId: EventStreamId): List<EventAggregate>
}
