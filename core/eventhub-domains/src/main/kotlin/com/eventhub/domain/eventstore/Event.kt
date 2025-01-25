package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.Bucket.BucketId
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
    val message: Message,
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
            .fetch(
                eventId = this,
                ownerId = ownerId,
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
        message.checkJson()

        checkEventStreamOwnership(eventStreamRepository)

        checkEventStreamBucket(bucketRepository)

        // check identity

        eventStoreRepository.store(this.toEventStore())
        // store related identifiers for each
    }

    private suspend fun checkEventStreamBucket(bucketRepository: BucketRepository): BucketId {
        bucketRepository.fetch(eventStreamId)?.let {
            return it
        }

        val bucketId =
            bucketRepository
                .fetch(ownerId)
                .random()

        bucketRepository.store(
            bucketId,
            eventStreamId,
        )

        return bucketId
    }

    private suspend fun checkEventStreamOwnership(eventStreamRepository: EventStreamRepository) {
        val eventStreamExists = eventStreamId.exists(eventStreamRepository)

        if (eventStreamExists.not()) {
            eventStreamRepository.store(
                eventStreamId,
                ownerId,
            )
        }
    }

    private fun Message.checkJson() = Json.parseToJsonElement(this.data)
}
