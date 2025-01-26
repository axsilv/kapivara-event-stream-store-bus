package com.eventhub.domain.eventbus

import com.eventhub.domain.eventbus.Bucket.BucketId
import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Owner.OwnerId
import kotlinx.datetime.Instant

interface BucketRepository {
    suspend fun store(
        bucketId: BucketId,
        eventStreamId: EventStreamId,
    )

    suspend fun store(
        bucketId: BucketId,
        ownerId: OwnerId
    )

    suspend fun deliver(
        bucketId: BucketId,
        eventId: EventId
    )

    suspend fun delivering(
        bucketId: BucketId,
        eventId: EventId,
        timeout: Instant
    )

    suspend fun delivered(
        bucketId: BucketId,
        eventId: EventId,
    )

    suspend fun fetch(eventStreamId: EventStreamId): Bucket?

    suspend fun fetch(
        bucketId: BucketId,
        eventStreamId: EventStreamId,
    ): Bucket?

    suspend fun fetch(
        bucketId: BucketId,
        eventStreamId: EventStreamId,
        eventId: EventId,
    ): Bucket?

    suspend fun fetch(ownerId: OwnerId): List<Bucket>
}
