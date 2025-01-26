package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.Bucket.BucketId
import com.eventhub.domain.eventbus.BucketRepository
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Identity.IdentityId
import com.eventhub.domain.eventstore.Owner.OwnerId
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository
import com.eventhub.domain.eventstore.ports.IdentityRepository
import com.eventhub.domain.eventstore.ports.CorrelationIdRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import java.util.UUID

data class Event(
    val eventId: EventId,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val message: Message,
    val eventStreamId: EventStreamId,
    val ownerId: OwnerId,
    val identityId: IdentityId,
    val isCanary: Boolean,
    val isTest: Boolean,
    val position: Long
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

    suspend fun store(
        eventStoreRepository: EventStoreRepository,
        eventStreamRepository: EventStreamRepository,
        bucketRepository: BucketRepository,
        identityRepository: IdentityRepository,
        correlationIdRepository: CorrelationIdRepository,
    ) {
        message.checkJson()

        checkEventStreamOwnership(eventStreamRepository = eventStreamRepository)

        val bucketId = checkEventStreamBucket(bucketRepository = bucketRepository)

        checkIdentity(identityRepository = identityRepository)

        eventStoreRepository.store(eventStore = this.toEventStore())

        storeCorrelationIds(correlationIdRepository = correlationIdRepository)

        bucketRepository.deliver(
            bucketId = bucketId,
            eventId = eventId
        )
    }

    private suspend fun storeCorrelationIds(correlationIdRepository: CorrelationIdRepository) {
        message
            .correlationIds
            .map { correlationId ->
                coroutineScope {
                    launch {
                        correlationIdRepository.store(correlationId = correlationId)
                    }
                }
            }.joinAll()
    }

    private suspend fun checkIdentity(identityRepository: IdentityRepository) {
        if (identityRepository.exists(identityId = identityId).not()) {
            throw RuntimeException() // todo
        }
    }

    private suspend fun checkEventStreamBucket(bucketRepository: BucketRepository): BucketId {
        bucketRepository.fetch(eventStreamId = eventStreamId)?.let { bucket ->
            return bucket.id
        }

        return storeBucketForStream(bucketRepository)
    }

    private suspend fun storeBucketForStream(bucketRepository: BucketRepository): BucketId {
        val bucket =
            bucketRepository
                .fetch(ownerId = ownerId)
                .random()

        bucketRepository.store(
            bucketId = bucket.id,
            eventStreamId = eventStreamId
        )

        return bucket.id
    }

    private suspend fun checkEventStreamOwnership(eventStreamRepository: EventStreamRepository) {
        val eventStreamExists = eventStreamId.exists(eventStreamRepository)

        if (eventStreamExists.not()) {
            eventStreamRepository.store(
                eventStreamId = eventStreamId,
                ownerId = ownerId,
            )
        }
    }

    private fun Message.checkJson() = Json.parseToJsonElement(this.payload)
}
