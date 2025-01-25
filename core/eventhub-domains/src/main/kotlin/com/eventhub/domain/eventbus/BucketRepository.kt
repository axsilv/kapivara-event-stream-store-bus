package com.eventhub.domain.eventbus

import com.eventhub.domain.eventbus.Bucket.BucketId
import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.EventStream.EventStreamId

interface BucketRepository {
    suspend fun store(
        bucketId: BucketId,
        eventStreamId: EventStreamId,
    )

    suspend fun fetch(eventStreamId: EventStreamId): BucketId?

    suspend fun fetch(
        bucketId: BucketId,
        eventStreamId: EventStreamId,
    ): BucketId?

    suspend fun fetch(
        bucketId: BucketId,
        eventStreamId: EventStreamId,
        eventId: Event.EventId,
    ): BucketId?

    suspend fun fetch(ownerId: Event.OwnerId): List<BucketId>
}
