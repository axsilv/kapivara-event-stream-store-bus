package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.ports.EventStore
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.util.UUID

object EventTestFixture {
    val eventUuid: UUID = UUID.randomUUID()
    val eventStreamUuid: UUID = UUID.randomUUID()
    val ownerId: UUID = UUID.randomUUID()
    private val identityId: UUID = UUID.randomUUID()

    private val occurredOn = Clock.System.now()

    fun event(shouldSendToEventBus: Boolean): Event =
        Event(
            eventId = eventUuid.toEventId(),
            metadata = mapOf(),
            occurredOn = occurredOn,
            message =
                Message(
                    owner = "test_owner",
                    type = "test_type",
                    alias = "test_alias",
                    relatedIdentifiers = listOf(),
                    data = Json.encodeToString(mapOf("test" to "true")),
                ),
            eventStreamId = EventStreamId(eventStreamUuid),
            shouldSendToEventBus = shouldSendToEventBus,
            ownerId = ownerId.toOwnerId(),
            identityId = identityId,
        )

    fun eventStore() =
        EventStore(
            eventId = eventUuid,
            metadata = mapOf(),
            occurredOn = occurredOn,
            owner = "test_owner",
            type = "test_type",
            alias = "test_alias",
            relatedIdentifiers = mapOf(),
            data = Json.encodeToJsonElement(mapOf("test" to "true")),
            eventStreamId = eventStreamUuid,
            shouldSendToEventBus = true,
            ownerId = ownerId,
            identityId = identityId,
        )
}
