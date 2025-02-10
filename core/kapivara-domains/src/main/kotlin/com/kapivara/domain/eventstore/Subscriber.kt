package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventbus.EventBusBucket.EventBusBucketId
import com.kapivara.domain.eventstore.Identity.IdentityId
import com.kapivara.domain.eventstore.ports.SubscriberRepository

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

    suspend fun store(subscriberRepository: SubscriberRepository) = subscriberRepository.store(this)
}
