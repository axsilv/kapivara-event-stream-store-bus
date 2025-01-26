package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.ports.EventIdentityRepository

data class EventIdentity(
    val id: IdentityId,
    val name: String,
    val eventPublisher: EventPublisher,
    val eventSubscribers: List<EventSubscriber>,
    val metadata: Map<String, String>,
) {
    data class IdentityId(
        private val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(eventIdentityRepository: EventIdentityRepository) {
        eventIdentityRepository.store(this)
    }
}
