package com.eventhub.domain.eventstore

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Message.RelatedIdentifier.RelatedIdentifierId
import com.eventhub.domain.eventstore.ports.EventStore
import java.util.UUID

fun EventStore.toEvent() =
    Event(
        eventId = eventId.toEventId(),
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
        owner = message.owner,
        type = message.type,
        alias = message.alias,
        relatedIdentifiers = message.relatedIdentifiers.associate { it.relatedIdentifierId.toString() to it.owner },
        data = message.data,
        eventStreamId = eventStreamId.toEventStreamId(),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = ownerId.toOwnerId(),
        identityId = identityId.toIdentityId(),
    )

fun UUID.toEventId(): EventId = EventId(value = this)

fun UUID.toEventStreamId(): EventStreamId = EventStreamId(value = this)

fun UUID.toOwnerId() = OwnerId(value = this)

fun UUID.toIdentityId() = Event.IdentityId(value = this)
