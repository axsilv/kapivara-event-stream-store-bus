package com.kapivara.services.eventstore

import com.kapivara.domain.eventbus.ports.EventBusBucketRepository
import com.kapivara.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.kapivara.domain.eventstore.Message
import com.kapivara.domain.eventstore.ports.IdentityRepository
import com.kapivara.domain.eventstore.ports.PublisherRepository
import com.kapivara.domain.eventstore.ports.StreamRepository
import com.kapivara.domain.eventstore.toEventMessageId
import com.kapivara.domain.eventstore.toEventStreamId
import com.kapivara.domain.eventstore.toIdentityId
import com.kapivara.domain.eventstore.toPublisherId
import com.kapivara.services.CommandBusinessHandler

class StoreEventMessageBusinessHandler(
    private val streamRepository: StreamRepository,
    private val identityRepository: IdentityRepository,
    private val eventBusBucketRepository: EventBusBucketRepository,
    private val eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
    private val publisherRepository: PublisherRepository,
) : CommandBusinessHandler<StoreEventMessageBusiness> {
    override suspend fun store(commandBusiness: StoreEventMessageBusiness) {
        Message(
            id = commandBusiness.id.toEventMessageId(),
            identityId = commandBusiness.identityId.toIdentityId(),
            publisherId = commandBusiness.publisherId.toPublisherId(),
            streamId = commandBusiness.eventStreamId.toEventStreamId(),
            payload = commandBusiness.payload,
            position = commandBusiness.position,
            isFinal = commandBusiness.isFinal,
            occurredOn = commandBusiness.occurredOn,
        ).store(
            streamRepository = streamRepository,
            identityRepository = identityRepository,
            eventBusBucketRepository = eventBusBucketRepository,
            eventBusDeliveryControlRepository = eventBusDeliveryControlRepository,
            publisherRepository = publisherRepository,
        )
    }
}
