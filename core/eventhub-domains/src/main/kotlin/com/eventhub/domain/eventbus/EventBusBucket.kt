package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.ports.EventBusBucketRepository
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId

data class EventBusBucket(
    val id: EventBusBucketId,
    val subscriberId: SubscriberId,
    val streams: List<EventStream>,
) {
    suspend fun store(eventBusBucketRepository: EventBusBucketRepository) = eventBusBucketRepository.store(this)

    data class EventBusBucketId(
        private val value: Long,
    ) : Identifier<Long>(value = value)
}
