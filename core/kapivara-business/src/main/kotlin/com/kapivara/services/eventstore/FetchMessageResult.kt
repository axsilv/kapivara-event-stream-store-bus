package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Message
import kotlinx.datetime.Instant
import java.util.UUID

data class FetchMessageResult(
    val id: UUID,
    val messageName: String,
    val payloadFormat: String,
    val payloadType: String,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
)

fun Message.toResult() =
    FetchMessageResult(
        id = id.value,
        messageName = messageName,
        payloadFormat = payloadFormat,
        payloadType = payloadType,
        payload = payload,
        position = position,
        isFinal = isFinal,
        occurredOn = occurredOn,
    )
