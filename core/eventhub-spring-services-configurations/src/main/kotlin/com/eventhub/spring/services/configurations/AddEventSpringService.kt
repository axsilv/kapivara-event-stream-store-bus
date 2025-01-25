package com.eventhub.spring.services.configurations

import com.eventhub.domain.eventbus.BucketRepository
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository
import com.eventhub.services.eventstore.AddEvent
import com.eventhub.services.eventstore.AddEventService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddEventSpringService(
    val eventStoreRepository: EventStoreRepository,
    eventBusRepository: EventBusRepository,
    eventStreamRepository: EventStreamRepository,
    bucketRepository: BucketRepository,
) : AddEventService(
        eventStoreRepository = eventStoreRepository,
        eventBusRepository = eventBusRepository,
        eventStreamRepository = eventStreamRepository,
        bucketRepository = bucketRepository,
    ) {
    @Transactional
    override suspend fun add(addEvent: AddEvent) {
        super.add(addEvent = addEvent)
    }
}
