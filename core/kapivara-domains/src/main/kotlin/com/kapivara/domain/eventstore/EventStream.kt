package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventbus.EventBusBucket.Companion.deliverToSubscriber
import com.kapivara.domain.eventbus.ports.EventBusBucketRepository
import com.kapivara.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.kapivara.domain.eventstore.ports.EventIdentityRepository
import com.kapivara.domain.eventstore.ports.EventStreamRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

data class EventStream(
    val eventMessages: Set<EventMessage>,
) {
    companion object {
        private val log = KotlinLogging.logger { }

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
            } catch (e: Exception) {
                log.error { "event stream error ${e.localizedMessage}" }

                // TODO - add metrics
                throw e
            }
        }

        fun UUID.toEventStreamId() = EventStreamId(this)
    }

    class EventStreamId(
        val value: UUID,
    ) : Identifier<UUID>(value = value)
}
