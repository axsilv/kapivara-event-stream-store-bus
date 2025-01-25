package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.Identifier
import kotlinx.datetime.Instant
import java.util.UUID

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
) {
    data class OwnerId(
        private val value: UUID,
    ) : Identifier(value = value)

    data class IdentityId(
        private val value: UUID,
    ) : Identifier(value = value)

    data class EventStreamId(
        private val value: UUID,
    ) : Identifier(value = value)

    data class EventId(
        private val value: UUID,
    ) : Identifier(value = value)
}
