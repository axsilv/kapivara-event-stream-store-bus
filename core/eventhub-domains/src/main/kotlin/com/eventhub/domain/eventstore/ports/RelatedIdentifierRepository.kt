package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Message.RelatedIdentifier

interface RelatedIdentifierRepository {
    suspend fun store(identifier: RelatedIdentifier)
}
