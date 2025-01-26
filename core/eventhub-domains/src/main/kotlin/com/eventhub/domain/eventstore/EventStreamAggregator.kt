package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.EventBusBucketRepository
import com.eventhub.domain.eventstore.EventPublisher.PublisherId
import com.eventhub.domain.eventstore.EventSubscriber.SubscriberId
import com.eventhub.domain.eventstore.ports.EventAggregateRepository
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import com.eventhub.domain.eventstore.ports.EventStreamAggregatorRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID
import kotlin.collections.flatten

data class EventStreamAggregator(
    val id: EventStreamAggregatorId,
    val subscribers: List<SubscriberId>,
    val publishers: List<PublisherId>,
    val stream: List<EventAggregate>,
    val createdAt: Instant,
) {
    companion object {
        suspend fun finishStream(
            eventStreamAggregatorRepository: EventStreamAggregatorRepository,
            eventAggregateRepository: EventAggregateRepository,
            eventIdentityRepository: EventIdentityRepository,
            eventStreamAggregatorId: EventStreamAggregatorId,
            eventBusBucketRepository: EventBusBucketRepository,
        ) = supervisorScope {
            try {
                val aggregates = eventAggregateRepository.fetch(eventStreamAggregatorId)
                val subscribersId =
                    aggregates
                        .map { it.identityId }
                        .map {
                            async { eventIdentityRepository.fetchSubscribersId(it) }
                        }.awaitAll()
                        .flatten()
                val publishers = aggregates.map { it.publisherId }

                eventStreamAggregatorRepository.store(
                    EventStreamAggregator(
                        eventStreamAggregatorId,
                        subscribersId,
                        publishers,
                        aggregates,
                        Clock.System.now(),
                    ),
                )

                subscribersId.forEach { subscriberId ->
                    eventBusBucketRepository.deliver(
                        subscriberId,
                        eventStreamAggregatorId,
                    )
                }
            } catch (_: Exception) {
                // TODO - add log + metrics
            }
        }
    }

    data class EventStreamAggregatorId(
        private val value: UUID,
    ) : Identifier<UUID>(value = value)
}
