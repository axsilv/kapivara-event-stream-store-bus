package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Event
import kotlinx.datetime.Instant
import java.util.UUID

data class FetchEventResult(
    val id: UUID,
    val eventName: String,
    val payloadFormat: String,
    val payloadType: String,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
)

fun Event.toResult() =
    FetchEventResult(
        id = id.value,
        eventName = eventName,
        payloadFormat = payloadFormat,
        payloadType = payloadType,
        payload = payload,
        position = position,
        isFinal = isFinal,
        occurredOn = occurredOn,
    )
