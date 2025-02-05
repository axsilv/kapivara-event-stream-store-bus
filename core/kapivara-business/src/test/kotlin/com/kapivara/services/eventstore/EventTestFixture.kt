package com.kapivara.services.eventstore

import com.eventhub.domain.eventstore.EventStream.AggregateId
import com.eventhub.domain.eventstore.Message
import com.eventhub.domain.eventstore.toEventId
import com.eventhub.domain.eventstore.toOwnerId
import java.util.UUID
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

object EventTestFixture {
    val eventUuid: UUID = UUID.randomUUID()
    val eventStreamUuid: UUID = UUID.randomUUID()
    val ownerId: UUID = UUID.randomUUID()
    private val identityId: UUID = UUID.randomUUID()
    private val occurredOn = Clock.System.now()

    fun event(): Event =
        Event(
            id = eventUuid.toEventId(),
            metadata = mapOf(),
            occurredOn = occurredOn,
            message =
                Message(
                    owner = "test_owner",
                    type = "test_type",
                    alias = "test_alias",
                    correlationIds = listOf(),
                    payload = Json.encodeToJsonElement(mapOf("test" to "true")),
                ),
            aggregateId = AggregateId(eventStreamUuid),
            shouldSendToEventBus = true,
            subscriberId = ownerId.toOwnerId(),
            identityId = this@EventTestFixture.identityId,
        )
}
