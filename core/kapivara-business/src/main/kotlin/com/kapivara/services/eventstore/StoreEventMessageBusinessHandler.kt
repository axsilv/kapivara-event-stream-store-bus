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
import com.kapivara.services.CommandHandler

class StoreEventMessageBusinessHandler(
    private val eventStreamRepository: EventStreamRepository,
    private val eventIdentityRepository: EventIdentityRepository,
    private val eventBusBucketRepository: EventBusBucketRepository,
    private val eventBusDeliveryControlRepository: EventBusDeliveryControlRepository,
    private val eventPublisherRepository: EventPublisherRepository,
) : CommandHandler<StoreEventMessageBusiness> {
    override suspend fun store(command: StoreEventMessageBusiness) {
        EventMessage(
            id = command.id.toEventMessageId(),
            identityId = command.identityId.toIdentityId(),
            publisherId = command.publisherId.toPublisherId(),
            eventStreamId = command.eventStreamId.toEventStreamId(),
            payload = command.payload,
            position = command.position,
            isFinal = command.isFinal,
            occurredOn = command.occurredOn,
        ).store(
            eventStreamRepository = eventStreamRepository,
            eventIdentityRepository = eventIdentityRepository,
            eventBusBucketRepository = eventBusBucketRepository,
            eventBusDeliveryControlRepository = eventBusDeliveryControlRepository,
            eventPublisherRepository = eventPublisherRepository,
        )
    }
}
