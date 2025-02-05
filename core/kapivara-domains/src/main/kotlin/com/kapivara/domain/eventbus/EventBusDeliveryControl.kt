package com.kapivara.domain.eventbus

import com.kapivara.domain.eventbus.EventBusBucket.EventBusBucketId
import com.kapivara.domain.eventstore.EventStream.EventStreamId
import kotlinx.datetime.Instant
import java.util.UUID

data class EventBusDeliveryControl(
    val id: UUID = UUID.randomUUID(),
    val eventStreamId: EventStreamId,
    val eventBusBucketId: EventBusBucketId,
    val hideUntil: Instant?,
)
