package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.domain.eventstore.EventIdentity.IdentityId
import com.eventhub.domain.eventstore.EventMessage
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.Companion.toEventStreamId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Publisher.PublisherId
import com.eventhub.domain.eventstore.ports.EventStreamRepository
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.encodeToJsonElement
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitOne
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.UUID.fromString

@Repository
class R2dbcEventStreamRepository(
    private val db: DatabaseClient,
) : EventStreamRepository {
    @Transactional(rollbackFor = [Throwable::class])
    override suspend fun store(eventMessage: EventMessage) {
        db
            .sql("SELECT messages FROM event_streams WHERE id = :id FOR UPDATE")
            .bind("id", eventMessage.eventStreamId.value)
            .map { row ->
                row["messages", String::class.java] ?: "[]"
            }.awaitOne()
            .also { currentStreamJson ->
                val currentStream: List<Map<String, String>> = Json.decodeFromString(currentStreamJson)
                val newStream = currentStream + eventMessage.toMap()
                val newStreamJson = Json.encodeToString(newStream)

                db
                    .sql(
                        """
                    UPDATE event_streams 
                    SET messages = CAST(:newStream AS JSONB)
                    WHERE id = CAST(:id AS UUID)
                """,
                    ).bind("newStream", newStreamJson)
                    .bind("id", eventMessage.eventStreamId.value)
                    .fetch()
                    .awaitRowsUpdated()
            }
    }

    @Transactional(rollbackFor = [Throwable::class])
    override suspend fun store(eventStream: EventStream) {
        val messages =
            eventStream
                .eventMessages
                .map { it.toMap() }
                .toList()
        val streamJson = if (messages.isEmpty()) JsonArray(emptyList()) else Json.encodeToJsonElement(messages)

        db
            .sql(
                """
            INSERT INTO event_streams (
                id, messages, stream_external_reference, stream_external_reference_hash, created_at
            ) VALUES (
                :id, CAST(:messages AS JSONB), :streamExternalReference, :streamExternalReferenceHash, :createdAt
            )
        """,
            ).bind("id", eventStream.id.value)
            .bind("messages", streamJson.toString())
            .bind("streamExternalReference", eventStream.streamExternalReference)
            .bind("streamExternalReferenceHash", eventStream.streamExternalReferenceHash.toInt())
            .bind("createdAt", eventStream.createdAt.toJavaInstant())
            .await()
    }

    override suspend fun fetch(eventStreamId: EventStreamId): EventStream? =
        db
            .sql("SELECT * FROM event_streams WHERE id = :id")
            .bind("id", eventStreamId.value)
            .map { row, _ ->
                val streamJson: String = row["messages", String::class.java] ?: "[]"
                val stream: List<Map<String, String>> = Json.decodeFromString(streamJson)

                EventStream(
                    id = row["id", UUID::class.java]!!.toEventStreamId(),
                    eventMessages = stream.map { it.fromMap() },
                    streamExternalReference = row["stream_external_reference", UUID::class.java]!!,
                    streamExternalReferenceHash = row["stream_external_reference_hash", Long::class.java]!!,
                    createdAt = row["created_at", java.time.Instant::class.java]!!.toKotlinInstant(),
                )
            }.awaitSingleOrNull()

    private fun EventMessage.toMap() =
        mapOf(
            "id" to this.id.value.toString(),
            "identityId" to this.identityId.value.toString(),
            "publisherId" to this.publisherId.value.toString(),
            "eventStreamId" to this.eventStreamId.value.toString(),
            "payload" to this.payload,
            "position" to this.position.toString(),
            "isFinal" to this.isFinal.toString(),
            "occurredOn" to this.occurredOn.toString(),
        )

    private fun Map<String, String>.fromMap() =
        EventMessage(
            id = EventMessage.EventMessageId(fromString(this["id"])),
            identityId = IdentityId(this["identityId"]!!.toLong()),
            publisherId = PublisherId(this["publisherId"]!!.toLong()),
            eventStreamId = EventStreamId(fromString(this["eventStreamId"]!!)),
            payload = this["payload"]!!,
            position = this["position"]!!.toLong(),
            isFinal = this["isFinal"]!!.toBoolean(),
            occurredOn =
                java.time.Instant
                    .parse(this["occurredOn"]!!)
                    .toKotlinInstant(),
        )
}
