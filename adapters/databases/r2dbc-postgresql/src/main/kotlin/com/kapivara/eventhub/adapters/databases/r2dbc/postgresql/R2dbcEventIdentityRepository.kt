package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.domain.eventstore.EventIdentity
import com.eventhub.domain.eventstore.EventIdentity.IdentityId
import com.eventhub.domain.eventstore.Publisher.PublisherId
import com.eventhub.domain.eventstore.Subscriber
import com.eventhub.domain.eventstore.Subscriber.SubscriberId
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import kotlinx.serialization.json.Json
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class R2dbcEventIdentityRepository(
    private val db: DatabaseClient,
) : EventIdentityRepository {
    override suspend fun store(eventIdentity: EventIdentity) {
        val subscribersJson = Json.encodeToString(eventIdentity.eventSubscribers)
        val metadataJson = Json.encodeToString(eventIdentity.metadata)

        return db
            .sql(
                """
            INSERT INTO event_identities (
                id, name, publisher_id, event_subscribers, metadata
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
            .sql("SELECT * FROM event_identities WHERE id = CAST(:id AS UUID)")
            .bind("id", identityId.value)
            .map { row, _ ->
                PublisherId(row["publisher_id", String::class.java]!!.toLong())
            }.awaitSingle()

    override suspend fun fetchSubscribersId(identityId: IdentityId): List<SubscriberId> {
        TODO("Not yet implemented")
    }
}
