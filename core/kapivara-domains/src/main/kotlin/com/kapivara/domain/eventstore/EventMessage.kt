package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventbus.ports.EventBusBucketRepository
import com.kapivara.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.kapivara.domain.eventstore.EventStream.Companion.deliverStream
import com.kapivara.domain.eventstore.EventStream.EventStreamId
import com.kapivara.domain.eventstore.Identity.IdentityId
import com.kapivara.domain.eventstore.Publisher.PublisherId
import com.kapivara.domain.eventstore.ports.EventIdentityRepository
import com.kapivara.domain.eventstore.ports.EventStreamRepository
import java.util.UUID
import kotlinx.datetime.Instant

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
