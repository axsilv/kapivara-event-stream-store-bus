package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.domain.eventstore.ports.EventStore
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Queries.INSERT_EVENT_STORE
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Queries.SELECT_BY_EVENT_ID_AND_OWNER_ID
import com.kapivara.eventhub.adapters.databases.r2dbc.postgresql.Queries.SELECT_BY_EVENT_STREAM_ID
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import kotlinx.serialization.json.Json
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class R2dbcEventStoreRepository(
    private val db: DatabaseClient,
) : EventStoreRepository {
    @Transactional(rollbackFor = [Throwable::class])
    override suspend fun store(eventStore: EventStore) {
        db
            .sql(INSERT_EVENT_STORE)
            .bind("eventId", eventStore.eventId)
            .bind("metadata", Json.encodeToString(eventStore.metadata))
            .bind("occurredOn", eventStore.occurredOn.toJavaInstant())
            .bind("owner", eventStore.owner)
            .bind("type", eventStore.type)
            .bind("alias", eventStore.alias)
            .bind("relatedIdentifiers", Json.encodeToString(eventStore.relatedIdentifiers))
            .bind("data", Json.encodeToString(eventStore.payload))
            .bind("eventStreamId", eventStore.eventStreamId)
            .bind("shouldSendToEventBus", eventStore.shouldSendToEventBus)
            .bind("ownerId", eventStore.ownerId)
            .bind("identityId", eventStore.identityId)
            .fetch()
            .awaitRowsUpdated()
    }

    override suspend fun get(
        eventId: UUID,
        ownerId: UUID,
    ): EventStore? =
        db
            .sql(SELECT_BY_EVENT_ID_AND_OWNER_ID)
            .bind("eventId", eventId)
            .bind("ownerId", ownerId)
            .map { row, _ ->
                rowToEventStore(row)
            }.awaitOneOrNull()

    override suspend fun getStream(eventStreamId: UUID): List<EventStore> =
        db
            .sql(SELECT_BY_EVENT_STREAM_ID)
            .bind("eventStreamId", eventStreamId)
            .map { row, _ ->
                rowToEventStore(row)
            }.flow()
            .toList()

    private fun rowToEventStore(row: Row): EventStore =
        EventStore(
            eventId = row["eventId", UUID::class.java]!!,
            metadata = rowToStringMap(row),
            occurredOn = (row["occurredOn", java.time.Instant::class.java]!!).toKotlinInstant(),
            owner = row["owner", String::class.java]!!,
            type = row["type", String::class.java]!!,
            alias = row["alias", String::class.java]!!,
            relatedIdentifiers = rowToStringMap(row),
            payload = row["data", String::class.java]!!,
            eventStreamId = row["eventStreamId", UUID::class.java]!!,
            shouldSendToEventBus = row["shouldSendToEventBus", Boolean::class.java]!!,
            ownerId = row["ownerId", UUID::class.java]!!,
            identityId = row["identityId", UUID::class.java]!!,
        )

    private fun rowToStringMap(row: Row): Map<String, String> {
        val json = row["relatedIdentifiers", String::class.java]!!

        return Json.decodeFromString<Map<String, String>>(json)
    }
}
