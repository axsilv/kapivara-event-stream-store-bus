package com.eventhub.spring.services.configurations

import com.eventhub.domain.eventbus.EventBusBucketRepository
import com.eventhub.services.eventstore.AddEvent
import com.eventhub.services.eventstore.AddEventService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddEventSpringService(
    val eventStoreRepository: EventStoreRepository,
    eventBusRepository: EventBusRepository,
    eventStreamRepository: EventStreamRepository,
    eventBusBucketRepository: EventBusBucketRepository,
) : AddEventService(
        eventStoreRepository = eventStoreRepository,
        eventBusRepository = eventBusRepository,
        eventStreamRepository = eventStreamRepository,
        eventBusBucketRepository = eventBusBucketRepository,
    ) {
    @Transactional
    override suspend fun add(addEvent: AddEvent) {
        super.add(addEvent = addEvent)
    }
}
