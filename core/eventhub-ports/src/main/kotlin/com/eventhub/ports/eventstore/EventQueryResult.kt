package com.eventhub.ports.eventstore

import kotlinx.datetime.Instant
import kotlinx.serialization.json.JsonElement
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
        val data: JsonElement,
    ) {
        data class RelatedIdentifierQueryResult(
            val relatedIdentifierId: UUID,
            val owner: String,
        )
    }
}
