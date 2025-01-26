package com.eventhub.spring.services.configurations

import com.eventhub.domain.eventbus.EventBusBucketRepository
import com.eventhub.services.eventstore.AddAllEventsService
import com.eventhub.services.eventstore.AddEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddAllEventsSpringService(
    eventStoreRepository: EventStoreRepository,
    eventBusRepository: EventBusRepository,
    eventStreamRepository: EventStreamRepository,
    eventBusBucketRepository: EventBusBucketRepository,
) : AddAllEventsService(
        eventStoreRepository = eventStoreRepository,
        eventBusRepository = eventBusRepository,
        eventStreamRepository = eventStreamRepository,
        eventBusBucketRepository = eventBusBucketRepository,
    ) {
    @Transactional
    override suspend fun addAll(addEvents: List<AddEvent>) {
        super.addAll(addEvents = addEvents)
    }
}
