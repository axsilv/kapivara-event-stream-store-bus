package com.eventhub.services.eventstore

import kotlinx.datetime.Instant
import java.util.UUID

data class EventQueryResult(
    val eventId: UUID,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val eventData: EventDataQueryResult,
    val eventStreamId: UUID,
    val shouldSendToEventBus: Boolean,
    val ownerId: UUID,
) {
    data class EventDataQueryResult(
        val owner: String,
        val type: String,
        val alias: String,
        val relatedIdentifiers: List<RelatedIdentifierQueryResult>,
        val data: String,
    ) {
        data class RelatedIdentifierQueryResult(
            val relatedIdentifierId: UUID,
            val owner: String,
        )
    }
}
