package com.kapivara.domain.eventbus

import com.kapivara.domain.eventbus.EventBusBucket.EventBusBucketId
import com.kapivara.domain.eventstore.EventStream.EventStreamId
import java.util.UUID
import kotlinx.datetime.Instant

data class EventBusDeliveryControl(
    val id: UUID = UUID.randomUUID(),
    val eventStreamId: EventStreamId,
    val eventBusBucketId: EventBusBucketId,
    val hideUntil: Instant?,
)
