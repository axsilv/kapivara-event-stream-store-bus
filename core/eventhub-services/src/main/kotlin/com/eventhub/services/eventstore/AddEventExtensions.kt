package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventData
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.toRelatedIdentifierId

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
