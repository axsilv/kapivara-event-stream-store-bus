package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.EventBusBucketRepository
import com.eventhub.domain.eventstore.EventIdentity.IdentityId
import com.eventhub.domain.eventstore.EventPublisher.PublisherId
import com.eventhub.domain.eventstore.EventStreamAggregator.Companion.collect
import com.eventhub.domain.eventstore.EventStreamAggregator.EventStreamAggregatorId
import com.eventhub.domain.eventstore.ports.EventAggregateRepository
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import com.eventhub.domain.eventstore.ports.EventStreamAggregatorRepository
import kotlinx.datetime.Instant
import java.util.UUID

data class EventAggregate(
    val id: EventAggregateId,
    val identityId: IdentityId,
    val publisherId: PublisherId,
    val eventStreamAggregatorId: EventStreamAggregatorId,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) {
    data class EventAggregateId(
        private val value: UUID,
    ) : Identifier<UUID>(value)

    suspend fun store(
        eventAggregateRepository: EventAggregateRepository,
        eventStreamAggregatorRepository: EventStreamAggregatorRepository,
        eventIdentityRepository: EventIdentityRepository,
        eventBusBucketRepository: EventBusBucketRepository,
    ) {
        eventIdentityRepository.fetchPublisherId(identityId).let {
            if (it != publisherId) {
                throw RuntimeException() // todo
            }
        }

        eventAggregateRepository.store(this)

        if (isFinal) {
            collect(
                eventStreamAggregatorRepository,
                eventAggregateRepository,
                eventIdentityRepository,
                eventStreamAggregatorId,
                eventBusBucketRepository,
            )
        }
    }
}
