package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import kotlinx.datetime.Instant
import java.util.UUID

data class Event(
    val id: EventId,
    val eventName: String,
    val payloadFormat: String,
    val payloadType: String,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) {
    data class EventId(
        val value: UUID,
    ) : Identifier<UUID>(value)
}
