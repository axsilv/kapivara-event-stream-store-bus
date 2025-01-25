package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.EventStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Message
import com.eventhub.domain.eventstore.toIdentityId
import com.eventhub.domain.eventstore.toOwnerId
import com.eventhub.domain.eventstore.toRelatedIdentifierId
import com.eventhub.services.eventstore.EventQueryResult.EventDataQueryResult
import com.eventhub.services.eventstore.EventQueryResult.EventDataQueryResult.RelatedIdentifierQueryResult

fun AddEvent.toEvent(): Event =
    Event(
        eventId = EventId(eventId),
        metadata = metadata,
        occurredOn = occurredOn,
        message =
            Message(
                owner = owner,
                type = type,
                alias = alias,
                relatedIdentifiers =
                    relatedIdentifiers.map { (key, value) ->
                        Message.RelatedIdentifier(
                            owner = value,
                            relatedIdentifierId = key.toRelatedIdentifierId(),
                        )
                    },
                data = data,
            ),
        eventStreamId = EventStreamId(value = eventStreamId),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = ownerId.toOwnerId(),
        identityId = identityId.toIdentityId(),
    )

fun Event.toEventQueryResult(): EventQueryResult =
    EventQueryResult(
        eventId = eventId.toUUID(),
        metadata = metadata,
        occurredOn = occurredOn,
        eventData =
            EventDataQueryResult(
                owner = message.owner,
                type = message.type,
                alias = message.alias,
                relatedIdentifiers =
                    message.relatedIdentifiers.map { (key, value) ->
                        RelatedIdentifierQueryResult(
                            owner = value,
                            relatedIdentifierId = key.toUUID(),
                        )
                    },
                data = message.data,
            ),
        eventStreamId = eventStreamId.toUUID(),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = ownerId.toUUID(),
    )

fun EventStream.toEventStreamQueryResult() =
    EventStreamQueryResult(
        eventStreamId = eventStreamId.toUUID(),
        events = events.map { it.toEventQueryResult() }.toList(),
    )
