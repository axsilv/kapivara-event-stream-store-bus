package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.domain.eventstore.EventIdentity
import com.eventhub.domain.eventstore.EventIdentity.IdentityId
import com.eventhub.domain.eventstore.Publisher.PublisherId
import com.eventhub.domain.eventstore.Subscriber.SubscriberId
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import kotlinx.serialization.json.Json
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitOne
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class R2dbcEventIdentityRepository(
    private val db: DatabaseClient,
) : EventIdentityRepository {
    override suspend fun store(eventIdentity: EventIdentity) {
        val subscribersId =
            eventIdentity
                .eventSubscribers
                .map { it.value }
        val subscribersJson =
            Json.encodeToString(
                subscribersId,
            )
        val metadataJson = Json.encodeToString(eventIdentity.metadata)

        return db
            .sql(
                """
            INSERT INTO identities (
                id, name, publisher_id, subscribers_id, metadata
            ) VALUES (
                CAST(:id AS UUID), :name, 
                CAST(:eventPublisherId AS UUID), 
                CAST(:eventSubscribers AS JSONB), CAST(:metadata AS JSONB)
            )
        """,
            ).bind("id", eventIdentity.id.value.toString())
            .bind("name", eventIdentity.name)
            .bind("publisherId", eventIdentity.publisherId.value)
            .bind("subscribersId", subscribersJson)
            .bind("metadata", metadataJson)
            .await()
    }

    override suspend fun fetchPublisherId(identityId: IdentityId): PublisherId? =
        db
            .sql("SELECT * FROM identities WHERE id = CAST(:id AS UUID)")
            .bind("id", identityId.value)
            .map { row, _ ->
                PublisherId(row["publisher_id", String::class.java]!!.toLong())
            }.awaitSingle()

    override suspend fun fetchSubscribersId(identityId: IdentityId): List<SubscriberId> =
        db
            .sql("SELECT * FROM identities WHERE id = CAST(:id AS UUID)")
            .bind("id", identityId.value)
            .map { row, _ ->
                val subscribersIdRow = row["subscribers_id", String::class.java]!!
                Json.decodeFromString<List<Long>>(subscribersIdRow).map {
                    SubscriberId(it)
                }
            }.awaitSingle()

    override suspend fun appendSubscriberId(
        eventIdentity: EventIdentity,
        subscriberId: SubscriberId,
    ) {
        db
            .sql("SELECT subscribers_id FROM identities WHERE id = :id FOR UPDATE")
            .bind("id", eventIdentity.id.value)
            .map { row ->
                row["subscribers_id", String::class.java] ?: "[]"
            }.awaitOne()
            .also { currentSubscribersId ->
                val subscribersId: List<Map<String, String>> = Json.decodeFromString(currentSubscribersId)
                val newSubscribersId = subscribersId + subscriberId.value
                val newSubscribersIdJson = Json.encodeToString(newSubscribersId)

                // todo
                db
                    .sql(
                        """
                    UPDATE event_streams 
                    SET messages = CAST(:newStream AS JSONB)
                    WHERE id = CAST(:id AS UUID)
                """,
                    ).bind("newStream", newSubscribersIdJson)
                    .bind("id", eventIdentity.id.value)
                    .fetch()
                    .awaitRowsUpdated()
            }
    }
}
