package com.kapivara.domain.eventbus

import com.kapivara.domain.eventbus.EventBusBucket.EventBusBucketId
import com.kapivara.domain.eventstore.Stream.StreamId
import kotlinx.datetime.Instant
import java.util.UUID

data class EventBusDeliveryControl(
    val id: UUID = UUID.randomUUID(),
    val streamId: StreamId,
    val eventBusBucketId: EventBusBucketId,
    val hideUntil: Instant?,
)
