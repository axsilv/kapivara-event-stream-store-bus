package com.kapivara.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.ports.EventPublisherRepository

data class Publisher(
    val id: PublisherId,
    val publisherName: String,
) {
    data class PublisherId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    suspend fun store(eventPublisherRepository: EventPublisherRepository) {
        eventPublisherRepository.store(this)
    }
}
