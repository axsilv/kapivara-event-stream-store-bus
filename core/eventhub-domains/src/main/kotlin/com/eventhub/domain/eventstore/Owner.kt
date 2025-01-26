package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.Bucket.BucketId
import java.util.UUID

data class Owner(
    val id: OwnerId,
    val description: String,
    val systemNameOrigin: String,
    val bucketIds: List<BucketId>
) {

    data class OwnerId(
        private val value: UUID,
    ) : Identifier(value = value)
}