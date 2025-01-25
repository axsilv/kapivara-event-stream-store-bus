package com.eventhub.services.eventstore

import java.util.UUID

data class EventStreamQueryResult(
    val eventStreamId: UUID,
    val events: List<EventQueryResult>,
)
