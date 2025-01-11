package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.ports.eventstore.EventStore
import java.util.UUID

fun EventStore.toEvent() =
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

fun Event.toEventStore() =
    EventStore(
        eventId = eventId.toUUID(),
        metadata = metadata,
        occurredOn = occurredOn,
        owner = eventData.owner,
        type = eventData.type,
        alias = eventData.alias,
        relatedIdentifiers = eventData.relatedIdentifiers.associate { it.relatedIdentifierId.value to it.owner },
        data = eventData.data,
        eventStreamId = eventStreamId.toUUID(),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = ownerId.toUUID(),
    )

fun UUID.toEventId(): EventId = EventId(value = this)
