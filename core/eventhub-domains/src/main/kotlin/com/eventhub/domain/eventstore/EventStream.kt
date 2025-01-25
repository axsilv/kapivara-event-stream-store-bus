package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import java.util.UUID

data class EventStream(
    val eventStreamId: EventStreamId,
    val events: List<Event>,
) {
    data class EventStreamId(
        private val value: UUID,
    ) : Identifier(value = value) {
        suspend fun get(eventStoreRepository: EventStoreRepository): EventStream =
            EventStream(
                eventStreamId = this,
                events =
                    eventStoreRepository
                        .getStream(eventStreamId = this.toUUID())
                        .map { it.toEvent() },
            )
    }
}
