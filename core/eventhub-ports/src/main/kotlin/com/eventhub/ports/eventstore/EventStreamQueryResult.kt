package com.eventhub.ports.eventstore

import java.util.UUID

data class EventStreamQueryResult(
    val eventStreamId: UUID,
    val events: List<EventQueryResult>,
)
