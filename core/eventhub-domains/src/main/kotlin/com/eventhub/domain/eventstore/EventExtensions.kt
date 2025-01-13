package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.ports.eventstore.AddEvent
import com.eventhub.ports.eventstore.EventQueryResult
import com.eventhub.ports.eventstore.EventQueryResult.EventDataQueryResult
import com.eventhub.ports.eventstore.EventQueryResult.EventDataQueryResult.RelatedIdentifierQueryResult
import com.eventhub.ports.eventstore.EventStore
import com.eventhub.ports.eventstore.EventStreamQueryResult
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

fun UUID.toEventStreamId(): EventStreamId = EventStreamId(value = this)

fun Event.toEventQueryResult(): EventQueryResult =
    EventQueryResult(
        eventId = eventId.toUUID(),
        metadata = metadata,
        occurredOn = occurredOn,
        eventData =
            EventDataQueryResult(
                owner = eventData.owner,
                type = eventData.type,
                alias = eventData.alias,
                relatedIdentifiers =
                    eventData.relatedIdentifiers.map { (key, value) ->
                        RelatedIdentifierQueryResult(
                            owner = value,
                            relatedIdentifierId = key.toUUID(),
                        )
                    },
                data = eventData.data,
            ),
        eventStreamId = eventStreamId.toUUID(),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = ownerId.toUUID(),
    )

fun EventStream.toEventStreamQueryResult() =
    EventStreamQueryResult(
        eventStreamId = eventStreamId.toUUID(),
        events = events.map { it.toEventQueryResult() },
    )

fun AddEvent.toEvent(): Event =
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
