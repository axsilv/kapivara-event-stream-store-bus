package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Event
import com.kapivara.domain.eventstore.toEventId
import com.kapivara.services.CommandBusiness
import kotlinx.datetime.Instant
import java.util.UUID

data class StoreEvent(
    val id: UUID,
    val eventName: String,
    val payloadFormat: String,
    val payloadType: String,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) : CommandBusiness {
    fun toEvent(): Event =
        Event(
            id = id.toEventId(),
            eventName = eventName,
            payloadFormat = payloadFormat,
            payloadType = payloadType,
            payload = payload,
            position = position,
            isFinal = isFinal,
            occurredOn = occurredOn,
        )
}
