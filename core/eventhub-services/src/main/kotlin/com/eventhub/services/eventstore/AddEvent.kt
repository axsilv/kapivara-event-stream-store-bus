package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventData
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.toRelatedIdentifierId
import kotlinx.datetime.Instant
import kotlinx.serialization.json.JsonElement
import java.util.UUID

data class AddEvent(
    val eventId: UUID,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val owner: String,
    val type: String,
    val alias: String,
    val relatedIdentifiers: Map<UUID, String>,
    val data: JsonElement,
    val eventStreamId: UUID,
    val shouldSendToEventBus: Boolean,
    val ownerId: UUID,
) {
    fun toEvent(): Event =
        Event(
            eventId = EventId(eventId),
            metadata = metadata,
            occurredOn = occurredOn,
            eventData =
                EventData(
                    owner = owner,
                    type = type,
                    alias = alias,
                    relatedIdentifiers =
                        relatedIdentifiers.map { (key, value) ->
                            EventData.RelatedIdentifier(
                                owner = value,
                                relatedIdentifierId = key.toRelatedIdentifierId(),
                            )
                        },
                    data = data,
                ),
            eventStreamId = EventStreamId(value = eventStreamId),
            shouldSendToEventBus = shouldSendToEventBus,
            ownerId = OwnerId(ownerId),
        )
}
