package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.Publisher.PublisherId
import com.eventhub.domain.eventstore.Subscriber.SubscriberId
import com.eventhub.domain.eventstore.ports.EventIdentityRepository

data class Identity(
    val id: IdentityId,
    val name: String,
    val publisherId: PublisherId,
    val eventSubscribers: Set<SubscriberId>,
    val metadata: Map<String, String>,
) {
    data class IdentityId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(eventIdentityRepository: EventIdentityRepository) {
        eventIdentityRepository.store(this)
    }
}
