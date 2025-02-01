package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventbus.ports.EventBusBucketRepository
import com.eventhub.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.eventhub.domain.eventstore.EventIdentity.IdentityId
import com.eventhub.domain.eventstore.EventStream.Companion.deliverStream
import com.eventhub.domain.eventstore.EventStream.EventStreamId
import com.eventhub.domain.eventstore.Publisher.PublisherId
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository
import kotlinx.datetime.Instant
import java.util.UUID

data class EventMessage(
    val id: EventMessageId,
    val identityId: IdentityId,
    val publisherId: PublisherId,
    val eventStreamId: EventStreamId,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) {
    data class EventMessageId(
        val value: UUID,
    ) : Identifier<UUID>(value)

    suspend fun store(
        eventStreamRepository: EventStreamRepository,
        eventIdentityRepository: EventIdentityRepository,
        eventBusBucketRepository: EventBusBucketRepository,
        eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
    ) {
        eventIdentityRepository.fetchPublisherId(identityId).let {
            if (it != publisherId) {
                throw RuntimeException() // todo
            }
        }

        eventStreamRepository.store(this)

        deliverStream(
            eventStreamRepository,
            eventIdentityRepository,
            eventStreamId,
            eventBusBucketRepository,
            eventBusDeliveryControlRepository,
        )
    }
}
