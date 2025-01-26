package com.eventhub.domain.eventbus

import com.eventhub.domain.eventbus.EventBusBucket.EventBusBucketId
import com.eventhub.domain.eventstore.EventStreamAggregator.EventStreamAggregatorId
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId

interface EventBusBucketRepository {
    suspend fun deliver(
        eventBusBucketId: EventBusBucketId,
        eventStreamAggregatorId: EventStreamAggregatorId,
    )

    suspend fun delivered(
        eventBusBucketId: EventBusBucketId,
        eventStreamAggregatorId: EventStreamAggregatorId,
    )

    suspend fun fetch(eventSubscriberId: SubscriberId): List<EventBusBucketId>
}
