package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.ports.eventstore.EventStore
import com.eventhub.ports.eventstore.EventStoreRepository
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Queries.INSERT_EVENT_RELATED_IDENTIFIER
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Queries.INSERT_EVENT_STORE
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Queries.SELECT_BY_EVENT_ID_AND_OWNER_ID
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Queries.SELECT_BY_EVENT_STREAM_ID
import io.r2dbc.spi.Row
import java.util.UUID
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.Instant
import kotlinx.serialization.json.JsonElement
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class R2dbcEventStoreRepository(
    private val db: DatabaseClient,
) : EventStoreRepository {
    override suspend fun add(eventStore: EventStore) {
        db
            .sql(INSERT_EVENT_STORE)
            .bind("eventId", eventStore.eventId)
            .bind("metadata", eventStore.metadata)
            .bind("occurredOn", eventStore.occurredOn)
            .bind("owner", eventStore.owner)
            .bind("type", eventStore.type)
            .bind("alias", eventStore.alias)
            .bind("relatedIdentifiers", eventStore.relatedIdentifiers)
            .bind("data", eventStore.data)
            .bind("eventStreamId", eventStore.eventStreamId)
            .bind("shouldSendToEventBus", eventStore.shouldSendToEventBus)
            .bind("ownerId", eventStore.ownerId)
            .fetch()
            .rowsUpdated()

        eventStore.relatedIdentifiers.forEach { (key, _) ->
            db
                .sql(INSERT_EVENT_RELATED_IDENTIFIER)
                .bind("id", UUID.randomUUID())
                .bind("eventId", eventStore.eventId)
                .bind("relatedIdentifierId", key)
                .fetch()
        }
    }

    override suspend fun get(eventId: UUID): EventStore? =
        db
            .sql(SELECT_BY_EVENT_ID_AND_OWNER_ID)
            .bind("eventId", eventId)
            .bind("ownerId", "")
            .map { row, _ ->
                EventStore(
                    eventId = row["eventId", UUID::class.java]!!,
                    metadata = rowToMetadata(row),
                    occurredOn = row["occurredOn", Instant::class.java]!!,
                    owner = row["owner", String::class.java]!!,
                    type = row["type", String::class.java]!!,
                    alias = row["alias", String::class.java]!!,
                    relatedIdentifiers = rowToRelatedIdentifiers(row),
                    data = row["data", JsonElement::class.java]!!,
                    eventStreamId = row["eventStreamId", UUID::class.java]!!,
                    shouldSendToEventBus = row["shouldSendToEventBus", Boolean::class.java]!!,
                    ownerId = row["ownerId", UUID::class.java]!!,
                )
            }.awaitOneOrNull()

    override suspend fun getStream(eventStreamId: UUID): List<EventStore> =
        db
            .sql(SELECT_BY_EVENT_STREAM_ID)
            .bind("eventStreamId", eventStreamId)
            .map { row, _ ->
                EventStore(
                    eventId = row["eventId", UUID::class.java]!!,
                    metadata = rowToMetadata(row),
                    occurredOn = row["occurredOn", Instant::class.java]!!,
                    owner = row["owner", String::class.java]!!,
                    type = row["type", String::class.java]!!,
                    alias = row["alias", String::class.java]!!,
                    relatedIdentifiers = rowToRelatedIdentifiers(row),
                    data = row["data", JsonElement::class.java]!!,
                    eventStreamId = row["eventStreamId", UUID::class.java]!!,
                    shouldSendToEventBus = row["shouldSendToEventBus", Boolean::class.java]!!,
                    ownerId = row["ownerId", UUID::class.java]!!,
                )
            }.flow()
            .toList()

    @Suppress("UNCHECKED_CAST")
    private fun rowToRelatedIdentifiers(row: Row): Map<UUID, String> = row["relatedIdentifiers", Map::class.java] as Map<UUID, String>

    @Suppress("UNCHECKED_CAST")
    private fun rowToMetadata(row: Row): Map<String, String> = row["metadata", Map::class.java] as Map<String, String>
}
