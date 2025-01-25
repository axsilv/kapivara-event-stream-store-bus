package com.eventhub.services.eventstore

import com.eventhub.domain.eventbus.BucketRepository
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository

open class AddEventService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusRepository: EventBusRepository,
    private val eventStreamRepository: EventStreamRepository,
    private val bucketRepository: BucketRepository,
) {
    open suspend fun add(addEvent: AddEvent) =
        addEvent
            .toEvent()
            .store(
                eventStoreRepository = eventStoreRepository,
                eventBusRepository = eventBusRepository,
                eventStreamRepository = eventStreamRepository,
                bucketRepository = bucketRepository,
            )
}
