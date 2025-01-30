package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.EventBusDeliveryControl
import com.eventhub.domain.eventbus.ports.EventBusBucketRepository
import com.eventhub.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.eventhub.domain.eventstore.EventPublisher.PublisherId
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import com.eventhub.domain.eventstore.ports.EventStreamAggregatorRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import java.util.UUID
import kotlin.collections.flatten

data class EventStream(
    val id: EventStreamId,
    val subscribers: List<SubscriberId>,
    val publishers: List<PublisherId>,
    val stream: List<EventAggregate>,
    val createdAt: Instant,
) {
    companion object {
        suspend fun deliverStream(
            eventStreamAggregatorRepository: EventStreamAggregatorRepository,
            eventIdentityRepository: EventIdentityRepository,
            eventStreamId: EventStreamId,
            eventBusBucketRepository: EventBusBucketRepository,
            eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
        ) = coroutineScope {
            eventStreamAggregatorRepository
                .fetch(eventStreamId)
                .map { it.identityId }
                .map { async { eventIdentityRepository.fetchSubscribersId(it) } }
                .awaitAll()
                .flatten()
                .map { subscriberId ->
                    launch {
                        deliverToSubscriber(
                            eventBusBucketRepository,
                            subscriberId,
                            eventStreamId,
                            eventBusDeliveryControlRepository,
                        )
                    }
                }.joinAll()
        }

        private suspend fun deliverToSubscriber(
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

    data class EventStreamId(
        private val value: UUID,
    ) : Identifier<UUID>(value = value)
}
