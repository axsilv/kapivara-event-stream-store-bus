package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.Publisher

interface EventPublisherRepository {
    suspend fun store(publisher: Publisher)
}
