package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import java.util.UUID

data class Bucket(
    val bucketId: BucketId,
    val eventId: UUID,
    val eventStreamId: UUID,
    val position: Long,
) {
    data class BucketId(
        private val value: UUID,
    ) : Identifier(value = value)
}
