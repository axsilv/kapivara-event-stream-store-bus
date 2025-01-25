package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.Event.IdentityId
import com.eventhub.domain.eventstore.Event.OwnerId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import kotlinx.datetime.Instant

data class EventStore(
    val eventId: EventId,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val owner: String,
    val type: String,
    val alias: String,
    val relatedIdentifiers: Map<String, String>,
    val data: String,
    val eventStreamId: EventStreamId,
    val shouldSendToEventBus: Boolean,
    val ownerId: OwnerId,
    val identityId: IdentityId,
)
