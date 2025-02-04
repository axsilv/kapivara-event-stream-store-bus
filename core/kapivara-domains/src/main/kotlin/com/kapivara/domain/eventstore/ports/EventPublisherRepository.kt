package com.kapivara.domain.eventstore.ports

import com.eventhub.domain.eventstore.Publisher

interface EventPublisherRepository {
    suspend fun store(publisher: Publisher)
}
