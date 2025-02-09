package com.kapivara.adapters.databases.filedatabase

import com.kapivara.domain.eventstore.EventMessage
import com.kapivara.domain.eventstore.EventStream.EventStreamId
import java.util.UUID

object EventStreamCache {
    private val cache: DatabaseCache = DatabaseCache().create<UUID, Set<EventMessage>>()

    fun fetchFromCache(eventStreamId: EventStreamId): Set<EventMessage>? =
        cache
            .fetch<UUID, Set<EventMessage>>(eventStreamId.value)

    fun store(
        key: UUID,
        value: Set<EventMessage>,
    ) = cache.store(key, value)
}
