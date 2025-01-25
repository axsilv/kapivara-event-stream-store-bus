package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventData.RelatedIdentifier.RelatedIdentifierId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.ports.EventStore
import com.eventhub.domain.eventstore.ports.toEventId
import com.eventhub.domain.eventstore.ports.toEventStreamId
import com.eventhub.domain.eventstore.ports.toIdentityId
import com.eventhub.domain.eventstore.ports.toOwnerId
import java.util.UUID

fun EventStore.toEvent() =
    Event(
        eventId = eventId.toEventId(),
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
        eventStreamId = eventStreamId.toEventStreamId(),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = ownerId.toOwnerId(),
        identityId = identityId.toIdentityId(),
    )

fun String.toRelatedIdentifierId(): RelatedIdentifierId = UUID.fromString(this).toRelatedIdentifierId()

fun Event.toEventStore() =
    EventStore(
        eventId = eventId.toEventId(),
        metadata = metadata,
        occurredOn = occurredOn,
        owner = eventData.owner,
        type = eventData.type,
        alias = eventData.alias,
        relatedIdentifiers = eventData.relatedIdentifiers.associate { it.relatedIdentifierId.toString() to it.owner },
        data = eventData.data,
        eventStreamId = eventStreamId.toEventStreamId(),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = ownerId.toOwnerId(),
        identityId = identityId.toIdentityId(),
    )

fun UUID.toEventId(): EventId = EventId(value = this)

fun UUID.toEventStreamId(): EventStreamId = EventStreamId(value = this)

fun UUID.toOwnerId() = OwnerId(value = this)

fun UUID.toIdentityId() = Event.IdentityId(value = this)
