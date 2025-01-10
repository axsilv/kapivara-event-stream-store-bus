package com.eventhub.domain.eventstore

import com.eventhub.ports.eventstore.EventStoreRepository
import java.util.UUID

data class EventStream(
    val eventStreamId: EventStreamId,
    val events: List<Event>,
) {
    data class EventStreamId(
        private val id: UUID,
    ) {
        fun toUUID() = id

        override fun toString(): String = id.toString()

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
