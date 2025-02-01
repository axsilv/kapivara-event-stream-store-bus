package com.eventhub.domain.eventbus.ports

import com.eventhub.domain.eventbus.EventBusBucket
import com.eventhub.domain.eventstore.Subscriber

interface EventBusBucketRepository {
    suspend fun fetch(subscriberId: Subscriber.SubscriberId): List<EventBusBucket.EventBusBucketId>

    suspend fun store(eventBusBucket: EventBusBucket)
}
