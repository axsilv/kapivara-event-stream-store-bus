package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.Owner.OwnerId
import java.util.UUID
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

data class Bucket(
    val id: BucketId,
    val ownerId: OwnerId,
) {
    data class BucketId(
        private val value: UUID,
    ) : Identifier(value = value)

    suspend fun generate(
        ownerId: OwnerId,
        quantity: Long,
        bucketRepository: BucketRepository
    ): List<BucketId> = coroutineScope {
        (0..quantity).map {
            async {
                val bucketId = UUID.randomUUID().toBucketId()
                bucketRepository.store(
                    bucketId = bucketId,
                    ownerId = ownerId
                )
                bucketId
            }
        }.awaitAll()
    }
}
