package com.eventhub.services.eventstore

import com.eventhub.domain.eventbus.ports.EventBusBucketRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

open class AddAllEventsService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusRepository: EventBusRepository,
    private val eventStreamRepository: EventStreamRepository,
    private val eventBusBucketRepository: EventBusBucketRepository,
) {
    open suspend fun addAll(addEvents: List<AddEvent>) =
        coroutineScope {
            addEvents
                .map { addEvent ->
                    launch {
                        addEvent.toEvent().store(
                            eventStoreRepository = eventStoreRepository,
                            eventBusRepository = eventBusRepository,
                            eventStreamRepository = eventStreamRepository,
                            bucketRepository = eventBusBucketRepository,
                        )
                    }
                }.joinAll()
        }
}
