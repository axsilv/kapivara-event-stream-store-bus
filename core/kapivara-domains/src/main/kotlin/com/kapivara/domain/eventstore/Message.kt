package com.kapivara.domain.eventstore

import com.kapivara.domain.Identifier
import com.kapivara.domain.eventbus.ports.EventBusBucketRepository
import com.kapivara.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.kapivara.domain.eventstore.Identity.IdentityId
import com.kapivara.domain.eventstore.Publisher.PublisherId
import com.kapivara.domain.eventstore.Stream.Companion.deliverStream
import com.kapivara.domain.eventstore.Stream.StreamId
import com.kapivara.domain.eventstore.ports.IdentityRepository
import com.kapivara.domain.eventstore.ports.PublisherRepository
import com.kapivara.domain.eventstore.ports.StreamRepository
import kotlinx.datetime.Instant
import java.util.UUID

data class Message(
    val id: MessageId,
    val identityId: IdentityId,
    val publisherId: PublisherId,
    val streamId: StreamId,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) {
    data class MessageId(
        val value: UUID,
    ) : Identifier<UUID>(value)

    suspend fun store(
        streamRepository: StreamRepository,
        identityRepository: IdentityRepository,
        eventBusBucketRepository: EventBusBucketRepository,
        eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
        publisherRepository: PublisherRepository,
    ) {
        identityRepository.fetchPublisherId(identityId).let {
            if (it != publisherId) {
                throw RuntimeException() // todo
            }
        }

        streamRepository.store(this)

        deliverStream(
            streamRepository,
            identityRepository,
            streamId,
            eventBusBucketRepository,
            eventBusDeliveryControlRepository,
        )
    }
}
