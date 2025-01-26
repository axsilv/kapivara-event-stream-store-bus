package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.EventAggregate
import com.eventhub.domain.eventstore.EventStreamAggregator.EventStreamAggregatorId

interface EventAggregateRepository {
    suspend fun store(eventAggregate: EventAggregate)

    suspend fun fetch(eventStreamAggregatorId: EventStreamAggregatorId): List<EventAggregate>
}
