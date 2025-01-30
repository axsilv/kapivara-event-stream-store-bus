package com.eventhub.domain.eventbus.ports

import com.eventhub.domain.eventbus.EventBusBucket.EventBusBucketId
import com.eventhub.domain.eventbus.EventBusDeliveryControl
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import kotlinx.datetime.Instant

interface EventBusDeliveryControlRepository {
    suspend fun store(eventBusDeliveryControl: EventBusDeliveryControl)

    suspend fun deliver(eventBusDeliveryControl: EventBusDeliveryControl)

    suspend fun hide(
        eventStreamId: EventStreamId,
        eventBusBucketId: EventBusBucketId,
        hideUntil: Instant?,
    )

    suspend fun commit(
        eventStreamId: EventStreamId,
        eventBusBucketId: EventBusBucketId,
    )

    suspend fun fetchVisible(eventBusBucketId: EventBusBucketId)
}
