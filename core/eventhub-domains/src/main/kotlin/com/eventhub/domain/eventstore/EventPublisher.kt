package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.ports.EventPublisherRepository

data class EventPublisher(
    val id: PublisherId,
    val publisherName: String,
) {
    data class PublisherId(
        private val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(eventPublisherRepository: EventPublisherRepository) {
        eventPublisherRepository.store(this)
    }
}
