package com.eventhub.ports.eventstore

import java.util.UUID

interface EventStreamQueryService {
    suspend fun get(eventStreamId: UUID): EventStreamQueryResult
}
