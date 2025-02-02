package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.EventBusBucket
import com.eventhub.domain.eventstore.ports.EventSubscriberRepository

data class Subscriber(
    val id: SubscriberId,
    val description: String,
    val systemNameOrigin: String,
    val listening: List<EventIdentity>,
    val eventBusBuckets: List<EventBusBucket>,
) {
    data class SubscriberId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(eventSubscriberRepository: EventSubscriberRepository) = eventSubscriberRepository.store(this)
}
