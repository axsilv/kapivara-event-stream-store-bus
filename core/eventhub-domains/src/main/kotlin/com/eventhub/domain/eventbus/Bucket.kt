package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.EventStream
import java.util.UUID

data class Bucket(
    val bucketId: BucketId,
    val eventId: Event.EventId,
    val eventStreamId: EventStream.EventStreamId,
    val position: Long,
) {
    data class BucketId(
        private val value: UUID,
    ) : Identifier(value = value)
}
