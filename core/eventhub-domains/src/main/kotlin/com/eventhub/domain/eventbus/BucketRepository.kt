package com.eventhub.domain.eventbus

import com.eventhub.domain.eventstore.ports.EventStore.EventStreamId
import java.util.UUID

interface BucketRepository {
    suspend fun add(
        bucketId: Bucket.BucketId,
        eventStreamId: UUID,
    )

    suspend fun addStream(
        bucketId: Bucket.BucketId,
        eventStreamId: EventStreamId,
    )

    suspend fun get(eventStreamId: EventStreamId): Bucket.BucketId?

    suspend fun get(
        bucketId: Bucket.BucketId,
        eventStreamId: EventStreamId,
    ): Bucket.BucketId?

    suspend fun get(
        bucketId: Bucket.BucketId,
        eventStreamId: EventStreamId,
        eventId: UUID,
    ): Bucket.BucketId?

    suspend fun get(ownerId: UUID): List<Bucket.BucketId>
}
