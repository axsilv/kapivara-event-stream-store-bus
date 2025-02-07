package com.kapivara.services.eventstore

import com.kapivara.domain.eventbus.ports.EventBusBucketRepository
import com.kapivara.domain.eventbus.ports.EventBusDeliveryControlRepository
import com.kapivara.domain.eventstore.EventMessage
import com.kapivara.domain.eventstore.ports.EventIdentityRepository
import com.kapivara.domain.eventstore.ports.EventPublisherRepository
import com.kapivara.domain.eventstore.ports.EventStreamRepository
import com.kapivara.domain.eventstore.toEventMessageId
import com.kapivara.domain.eventstore.toEventStreamId
import com.kapivara.domain.eventstore.toIdentityId
import com.kapivara.domain.eventstore.toPublisherId
import com.kapivara.services.CommandBusinessHandler

class StoreEventMessageBusinessHandler(
    private val eventStreamRepository: EventStreamRepository,
    private val eventIdentityRepository: EventIdentityRepository,
    private val eventBusBucketRepository: EventBusBucketRepository,
    private val eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
    private val eventPublisherRepository: EventPublisherRepository,
) : CommandBusinessHandler<StoreEventMessageBusiness> {
    override suspend fun store(commandBusiness: StoreEventMessageBusiness) {
        EventMessage(
            id = commandBusiness.id.toEventMessageId(),
            identityId = commandBusiness.identityId.toIdentityId(),
            publisherId = commandBusiness.publisherId.toPublisherId(),
            eventStreamId = commandBusiness.eventStreamId.toEventStreamId(),
            payload = commandBusiness.payload,
            position = commandBusiness.position,
            isFinal = commandBusiness.isFinal,
            occurredOn = commandBusiness.occurredOn,
        ).store(
            eventStreamRepository = eventStreamRepository,
            eventIdentityRepository = eventIdentityRepository,
            eventBusBucketRepository = eventBusBucketRepository,
            eventBusDeliveryControlRepository = eventBusDeliveryControlRepository,
            eventPublisherRepository = eventPublisherRepository,
        )
    }
}
