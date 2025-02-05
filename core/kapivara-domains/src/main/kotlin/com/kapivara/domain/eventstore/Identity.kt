package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventstore.Publisher.PublisherId
import com.kapivara.domain.eventstore.Subscriber.SubscriberId
import com.kapivara.domain.eventstore.ports.EventIdentityRepository

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
