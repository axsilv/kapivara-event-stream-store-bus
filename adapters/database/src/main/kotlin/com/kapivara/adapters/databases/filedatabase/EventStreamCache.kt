package com.kapivara.adapters.databases.filedatabase

import com.kapivara.domain.eventstore.Event
import com.kapivara.domain.eventstore.Stream.StreamId
import java.util.UUID

object EventStreamCache {
    private val cache: DatabaseCache = DatabaseCache().create<UUID, Set<Event>>()

    fun fetchFromCache(streamId: StreamId): Set<Event>? =
        cache
            .fetch<UUID, Set<Event>>(streamId.value)

    fun store(
        key: UUID,
        value: Set<Event>,
    ) = cache.store(key, value)
}
