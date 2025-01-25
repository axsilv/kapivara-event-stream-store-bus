package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Message
import com.eventhub.domain.eventstore.toEventId
import com.eventhub.domain.eventstore.toOwnerId
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

    fun event(): Event =
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
                    data = Json.encodeToJsonElement(mapOf("test" to "true")),
                ),
            eventStreamId = EventStreamId(eventStreamUuid),
            shouldSendToEventBus = true,
            ownerId = ownerId.toOwnerId(),
            identityId = identityId,
        )
}
