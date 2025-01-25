package com.eventhub.spring.services.configurations

import com.eventhub.domain.eventbus.BucketRepository
import com.eventhub.domain.eventbus.EventBusRepository
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository
import com.eventhub.services.eventstore.AddAllEventsService
import com.eventhub.services.eventstore.AddEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddAllEventsSpringService(
    eventStoreRepository: EventStoreRepository,
    eventBusRepository: EventBusRepository,
    eventStreamRepository: EventStreamRepository,
    bucketRepository: BucketRepository,
) : AddAllEventsService(
        eventStoreRepository = eventStoreRepository,
        eventBusRepository = eventBusRepository,
        eventStreamRepository = eventStreamRepository,
        bucketRepository = bucketRepository,
    ) {
    @Transactional
    override suspend fun addAll(addEvents: List<AddEvent>) {
        super.addAll(addEvents = addEvents)
    }
}
