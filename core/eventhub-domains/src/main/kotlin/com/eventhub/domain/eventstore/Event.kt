package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.BucketRepository
import com.eventhub.domain.eventbus.EventBusRepository
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import java.util.UUID

data class Event(
    val eventId: EventId,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val eventData: EventData,
    val eventStreamId: EventStreamId,
    val shouldSendToEventBus: Boolean,
    val ownerId: OwnerId,
    val identityId: IdentityId,
) {
    data class EventId(
        private val value: UUID,
    ) : Identifier(value = value) {
        suspend fun get(
            eventStoreRepository: EventStoreRepository,
            ownerId: OwnerId,
        ) = eventStoreRepository
            .get(
                eventId = this.toUUID(),
                ownerId = ownerId.toUUID(),
            )?.toEvent()
    }

    data class OwnerId(
        private val value: UUID,
    ) : Identifier(value = value)

    data class IdentityId(
        private val value: UUID,
    ) : Identifier(value = value)

    suspend fun add(
        eventStoreRepository: EventStoreRepository,
        eventBusRepository: EventBusRepository,
        eventStreamRepository: EventStreamRepository,
        bucketRepository: BucketRepository,
    ) {
        validateIfDataIsJson()

        val bucket =
            bucketRepository.get(
                eventStreamId = eventStreamId.toUUID(),
            )

        if (bucket == null) {
            val bucketId =

                bucketRepository.add(
                    bucketId = eventStreamId,
                    eventStreamId = eventStreamId.toUUID(),
                )
        }

        eventStreamRepository.add(
            eventStreamId = eventStreamId.toUUID(),
            ownerId = ownerId.toUUID(),
        )

        eventStoreRepository.add(eventStore = this.toEventStore())

        eventBusRepository.add()
    }

    private fun validateIfDataIsJson() = Json.parseToJsonElement(this.eventData.data)
}
