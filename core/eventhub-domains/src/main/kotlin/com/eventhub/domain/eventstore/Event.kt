package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.EventStoreRepository
import kotlinx.datetime.Instant
import java.util.UUID

data class Event(
    val eventId: EventId,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val eventData: EventData,
    val eventStreamId: EventStreamId,
    val shouldSendToEventBus: Boolean
) {
    data class EventId(
        private val value: UUID,
    ) {
        fun toUUID() = value

        override fun toString() = value.toString()

        suspend fun get(eventStoreRepository: EventStoreRepository) =
            eventStoreRepository
                .get(eventId = this.toUUID())
                .toEvent()
    }

    suspend fun add(
        eventStoreRepository: EventStoreRepository,
        eventBusClient: EventBusClient,
    ) {
        eventStoreRepository.add(this.toEventStore())

        if (shouldSendToEventBus) {
            eventBusClient.send().await()
        }
    }
}
