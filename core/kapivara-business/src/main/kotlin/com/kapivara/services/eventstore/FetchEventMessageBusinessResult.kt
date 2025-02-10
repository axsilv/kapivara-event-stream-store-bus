package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Message
import com.kapivara.services.QueryBusinessResult
import kotlinx.datetime.Instant
import java.util.UUID

data class FetchEventMessageBusinessResult(
    val id: UUID,
    val identityId: Long,
    val publisherId: Long,
    val eventStreamId: UUID,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) : QueryBusinessResult

fun Message.toResult() =
    FetchEventMessageBusinessResult(
        id = id.value,
        identityId = identityId.value,
        publisherId = publisherId.value,
        eventStreamId = streamId.value,
        payload = payload,
        position = position,
        isFinal = isFinal,
        occurredOn = occurredOn,
    )
