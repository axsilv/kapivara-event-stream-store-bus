package com.eventhub.domain.eventbus

import com.eventhub.domain.eventbus.EventBusBucket.BucketId
import com.eventhub.domain.eventstore.EventAggregate.EventAggregateId
import com.eventhub.domain.eventstore.EventStreamAggregator.EventStreamAggregatorId
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId

interface EventBusBucketRepository {
    suspend fun deliver(
        subscriberId: SubscriberId,
        eventStreamAggregatorId: EventStreamAggregatorId,
    )

    suspend fun delivered(
        bucketId: BucketId,
        eventAggregateId: EventAggregateId,
    )
}
