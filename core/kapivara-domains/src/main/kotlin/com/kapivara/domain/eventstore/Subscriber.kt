package com.kapivara.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.EventBusBucket.EventBusBucketId
import com.eventhub.domain.eventstore.Identity.IdentityId
import com.eventhub.domain.eventstore.ports.EventSubscriberRepository

data class Subscriber(
    val id: SubscriberId,
    val description: String,
    val systemNameOrigin: String,
    val listening: List<IdentityId>,
    val eventBusBuckets: List<EventBusBucketId>,
) {
    data class SubscriberId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(eventSubscriberRepository: EventSubscriberRepository) = eventSubscriberRepository.store(this)
}
