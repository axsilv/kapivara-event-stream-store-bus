package com.eventhub.domain.eventbus

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.ports.EventBusBucketRepository
import com.eventhub.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId

data class EventBusBucket(
    val id: EventBusBucketId,
    val subscriberId: SubscriberId,
    val streams: List<EventStreamId>,
) {
    data class EventBusBucketId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    companion object {
        suspend fun deliverToSubscriber(
            eventBusBucketRepository: EventBusBucketRepository,
            subscriberId: SubscriberId,
            eventStreamId: EventStreamId,
            eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
        ) {
            try {
                val eventBusBucketId =
                    eventBusBucketRepository
                        .fetch(subscriberId)
                        .random()

                eventBusDeliveryControlRepository.store(
                    EventBusDeliveryControl(
                        eventStreamId = eventStreamId,
                        eventBusBucketId = eventBusBucketId,
                        hideUntil = null,
                    ),
                )
            } catch (_: Exception) {
                // TODO - add log + metrics
            }
        }
    }

    suspend fun store(eventBusBucketRepository: EventBusBucketRepository) = eventBusBucketRepository.store(this)
}
