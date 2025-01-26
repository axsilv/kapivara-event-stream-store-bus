package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.Event.EventId
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Identity.IdentityId
import com.eventhub.domain.eventstore.Owner.OwnerId
import kotlinx.datetime.Instant

data class EventStore(
    val eventId: EventId,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val owner: String,
    val type: String,
    val alias: String,
    val payload: String,
    val eventStreamId: EventStreamId,
    val ownerId: OwnerId,
    val identityId: IdentityId,
)
