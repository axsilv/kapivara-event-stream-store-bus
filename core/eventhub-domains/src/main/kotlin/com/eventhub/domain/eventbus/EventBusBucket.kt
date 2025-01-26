package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.EventStreamAggregator
import com.eventhub.domain.eventstore.EventStreamAggregator.AggregateId
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.UUID

data class EventBusBucket(
    val id: BucketId,
    val subscriberId: SubscriberId,
    val streams: List<EventStreamAggregator>,
) {
    companion object {
        suspend fun generate(
            subscriberId: SubscriberId,
            quantity: Long,
            eventBusBucketRepository: EventBusBucketRepository,
        ): List<BucketId> =
            coroutineScope {
                (0..quantity)
                    .map {
                        async {
                            val bucketId = UUID.randomUUID().toBucketId()
                            eventBusBucketRepository.store(
                                bucketId = bucketId,
                                ownerId = subscriberId,
                            )
                            bucketId
                        }
                    }.awaitAll()
            }

        suspend fun generateCanary(
            subscriberId: SubscriberId,
            eventBusBucketRepository: EventBusBucketRepository,
        ): BucketId {
            val bucketId = UUID.randomUUID().toBucketId()

            eventBusBucketRepository.store(
                bucketId = bucketId,
                ownerId = subscriberId,
            )

            return bucketId
        }

        suspend fun checkEventStreamBucket(
            eventBusBucketRepository: EventBusBucketRepository,
            subscriberId: SubscriberId,
            eventIsCanary: Boolean,
            aggregateId: AggregateId,
        ): BucketId {
            eventBusBucketRepository.fetch(aggregateId = aggregateId)?.let { bucket ->

                if (bucket.isCanary != eventIsCanary) {
                    throw RuntimeException()
                }

                return bucket.id
            }

            return storeBucketForStream(
                eventBusBucketRepository = eventBusBucketRepository,
                subscriberId = subscriberId,
                eventIsCanary = eventIsCanary,
                aggregateId = aggregateId,
            )
        }

        private suspend fun storeBucketForStream(
            eventBusBucketRepository: EventBusBucketRepository,
            subscriberId: SubscriberId,
            eventIsCanary: Boolean,
            aggregateId: AggregateId,
        ): BucketId {
            val buckets =
                eventBusBucketRepository
                    .fetch(subscriberId = subscriberId)

            val bucket =
                try {
                    buckets
                        .filter { it.isCanary == eventIsCanary }
                        .random()
                } catch (_: NoSuchElementException) {
                    buckets.random()
                }

            eventBusBucketRepository.store(
                bucketId = bucket.id,
                aggregateId = aggregateId,
            )

            return bucket.id
        }
    }

    data class BucketId(
        private val value: UUID,
    ) : Identifier(value = value)
}
