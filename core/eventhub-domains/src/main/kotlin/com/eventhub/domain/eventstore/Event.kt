package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
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
    val shouldSendToEventBus: Boolean,
    val ownerId: OwnerId,
) {
    data class EventId(
        private val value: UUID,
    ) : Identifier(value = value) {
        suspend fun get(eventStoreRepository: EventStoreRepository) =
            eventStoreRepository
                .get(eventId = this.toUUID())
                .toEvent()
    }

    data class OwnerId(
        private val value: UUID,
    ) : Identifier(value = value)

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
