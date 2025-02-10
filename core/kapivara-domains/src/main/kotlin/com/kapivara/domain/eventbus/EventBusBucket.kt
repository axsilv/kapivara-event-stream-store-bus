package com.kapivara.domain.eventbus

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventbus.ports.EventBusBucketRepository
import com.kapivara.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.kapivara.domain.eventstore.Stream.StreamId
import com.kapivara.domain.eventstore.Subscriber.SubscriberId

data class EventBusBucket(
    val id: EventBusBucketId,
    val subscriberId: SubscriberId,
    val streams: List<StreamId>,
) {
    data class EventBusBucketId(
        val value: Long,
    ) : Identifier<Long>(value = value)

    companion object {
        suspend fun deliverToSubscriber(
            eventBusBucketRepository: EventBusBucketRepository,
            subscriberId: SubscriberId,
            streamId: StreamId,
            eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
        ) {
            try {
                val eventBusBucketId =
                    eventBusBucketRepository
                        .fetch(subscriberId)
                        .random()

                eventBusDeliveryControlRepository.store(
                    EventBusDeliveryControl(
                        streamId = streamId,
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
