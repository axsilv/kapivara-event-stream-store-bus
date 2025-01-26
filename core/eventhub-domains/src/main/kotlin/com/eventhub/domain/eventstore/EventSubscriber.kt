package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.EventBusBucket

data class EventSubscriber(
    val id: SubscriberId,
    val description: String,
    val systemNameOrigin: String,
    val listening: List<EventIdentity>,
    val eventBusBucket: EventBusBucket,
) {
    data class SubscriberId(
        private val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(ownerRepository: OwnerRepository) {
        ownerRepository.store(this)
    }
}
