package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.Message.CorrelationId
import java.util.UUID

data class Message(
    val correlationIds: List<CorrelationId>,
    val payload: String,
) {
    data class CorrelationId(
        private val value: UUID
    ) : Identifier(value = value)
}

fun UUID.toCorrelationId() = CorrelationId(value = this)
