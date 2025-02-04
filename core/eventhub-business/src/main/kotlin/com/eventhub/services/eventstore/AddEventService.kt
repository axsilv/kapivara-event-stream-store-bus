package com.eventhub.services.eventstore

import com.eventhub.domain.eventbus.ports.EventBusBucketRepository

open class AddEventService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusRepository: EventBusRepository,
    private val eventStreamRepository: EventStreamRepository,
    private val eventBusBucketRepository: EventBusBucketRepository,
) {
    open suspend fun add(addEvent: AddEvent) =
        addEvent
            .toEvent()
            .store(
                eventStoreRepository = eventStoreRepository,
                eventBusRepository = eventBusRepository,
                eventStreamRepository = eventStreamRepository,
                bucketRepository = eventBusBucketRepository,
            )
}
