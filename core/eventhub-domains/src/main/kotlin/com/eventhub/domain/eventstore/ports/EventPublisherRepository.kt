package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.EventPublisher

interface EventPublisherRepository {
    suspend fun store(eventPublisher: EventPublisher)
}
