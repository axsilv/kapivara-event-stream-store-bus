package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.EventBusBucket.Companion.deliverToSubscriber
import com.eventhub.domain.eventbus.ports.EventBusBucketRepository
import com.eventhub.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository
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
    val eventMessages: List<EventMessage>,
    val streamExternalReference: UUID,
    val streamExternalReferenceHash: Long,
    val createdAt: Instant,
) {
    companion object {
        suspend fun deliverStream(
            eventStreamRepository: EventStreamRepository,
            eventIdentityRepository: EventIdentityRepository,
            eventStreamId: EventStreamId,
            eventBusBucketRepository: EventBusBucketRepository,
            eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
        ) = coroutineScope {
            try {
                eventStreamRepository
                    .fetch(eventStreamId)
                    ?.eventMessages
                    ?.map { it.identityId }
                    ?.map { async { eventIdentityRepository.fetchSubscribersId(it) } }
                    ?.awaitAll()
                    ?.flatten()
                    ?.map { subscriberId ->
                        launch {
                            deliverToSubscriber(
                                eventBusBucketRepository,
                                subscriberId,
                                eventStreamId,
                                eventBusDeliveryControlRepository,
                            )
                        }
                    }?.joinAll()
            } catch (_: Exception) {
                // TODO - add log + metrics
            }
        }

        fun UUID.toEventStreamId() = EventStreamId(this)
    }

    class EventStreamId(
        val value: UUID,
    ) : Identifier<UUID>(value = value)
}
