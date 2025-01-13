package com.eventhub.ports.eventstore

import java.util.UUID

interface EventQueryService {
    suspend fun get(eventId: UUID): EventQueryResult
}
