package com.eventhub.services.eventstore

import kotlinx.datetime.Instant
import kotlinx.serialization.json.JsonElement
import java.util.UUID

data class AddEvent(
    val eventId: UUID,
    val metadata: Map<String, String>,
    val occurredOn: Instant,
    val owner: String,
    val type: String,
    val alias: String,
    val relatedIdentifiers: Map<UUID, String>,
    val data: JsonElement,
    val eventStreamId: UUID,
    val shouldSendToEventBus: Boolean,
    val ownerId: UUID,
)
