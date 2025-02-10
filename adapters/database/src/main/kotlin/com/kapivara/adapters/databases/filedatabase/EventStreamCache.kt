package com.kapivara.adapters.databases.filedatabase

import com.kapivara.domain.eventstore.Message
import com.kapivara.domain.eventstore.Stream.StreamId
import java.util.UUID

object EventStreamCache {
    private val cache: DatabaseCache = DatabaseCache().create<UUID, Set<Message>>()

    fun fetchFromCache(streamId: StreamId): Set<Message>? =
        cache
            .fetch<UUID, Set<Message>>(streamId.value)

    fun store(
        key: UUID,
        value: Set<Message>,
    ) = cache.store(key, value)
}
