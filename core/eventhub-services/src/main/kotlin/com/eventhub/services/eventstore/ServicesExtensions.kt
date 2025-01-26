package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.EventStreamAggregator
import com.eventhub.domain.eventstore.EventStreamAggregator.AggregateId
import com.eventhub.domain.eventstore.Message
import com.eventhub.domain.eventstore.toIdentityId
import com.eventhub.domain.eventstore.toOwnerId
import com.eventhub.domain.eventstore.toRelatedIdentifierId
import com.eventhub.services.eventstore.EventQueryResult.EventDataQueryResult
import com.eventhub.services.eventstore.EventQueryResult.EventDataQueryResult.RelatedIdentifierQueryResult

fun AddEvent.toEvent(): Event =
    Event(
        id = EventId(eventId),
        metadata = metadata,
        occurredOn = occurredOn,
        message =
            Message(
                owner = owner,
                type = type,
                alias = alias,
                correlationIds =
                    relatedIdentifiers.map { (key, value) ->
                        Message.CorrelationId(
                            owner = value,
                            relatedIdentifierId = key.toRelatedIdentifierId(),
                        )
                    },
                payload = data,
            ),
        aggregateId = AggregateId(value = eventStreamId),
        shouldSendToEventBus = shouldSendToEventBus,
        subscriberId = ownerId.toOwnerId(),
        identityId = this@toEvent.identityId.toIdentityId(),
    )

fun Event.toEventQueryResult(): EventQueryResult =
    EventQueryResult(
        eventId = id.toUUID(),
        metadata = metadata,
        occurredOn = occurredOn,
        eventData =
            EventDataQueryResult(
                owner = message.owner,
                type = message.type,
                alias = message.alias,
                relatedIdentifiers =
                    message.correlationIds.map { (key, value) ->
                        RelatedIdentifierQueryResult(
                            owner = value,
                            relatedIdentifierId = key.toUUID(),
                        )
                    },
                data = message.payload,
            ),
        eventStreamId = aggregateId.toUUID(),
        shouldSendToEventBus = shouldSendToEventBus,
        ownerId = subscriberId.toUUID(),
    )

fun EventStreamAggregator.toEventStreamQueryResult() =
    EventStreamQueryResult(
        eventStreamId = id.toUUID(),
        events = stream.map { it.toEventQueryResult() }.toList(),
    )
