package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.ports.eventstore.EventStore
import com.eventhub.ports.eventstore.EventStoreRepository
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.util.UUID

@Repository
class R2dbcEventStoreRepository(
    private val r2dbcPostgresqlConnection: Connection,
) : EventStoreRepository {
    override suspend fun add(eventStore: EventStore) {
        TODO("Not yet implemented")
    }

    override suspend fun get(eventId: UUID): EventStore {
        TODO("Not yet implemented")
    }

    override suspend fun getStream(eventStreamId: UUID): List<EventStore> {
        TODO("Not yet implemented")
    }
}
