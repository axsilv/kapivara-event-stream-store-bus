package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Message.CorrelationId

fun interface CorrelationIdRepository {
    suspend fun store(correlationId: CorrelationId)
}
