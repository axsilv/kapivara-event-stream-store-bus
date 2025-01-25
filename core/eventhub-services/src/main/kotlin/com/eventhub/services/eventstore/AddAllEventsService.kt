package com.eventhub.services.eventstore

import com.eventhub.domain.eventbus.BucketRepository
import com.eventhub.domain.eventbus.EventBusRepository
import com.eventhub.domain.eventstore.ports.EventStoreRepository
import com.eventhub.domain.eventstore.ports.EventStreamRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

open class AddAllEventsService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusRepository: EventBusRepository,
    private val eventStreamRepository: EventStreamRepository,
    private val bucketRepository: BucketRepository,
) {
    open suspend fun addAll(addEvents: List<AddEvent>) =
        coroutineScope {
            addEvents
                .map { addEvent ->
                    launch {
                        addEvent.toEvent().add(
                            eventStoreRepository = eventStoreRepository,
                            eventBusRepository = eventBusRepository,
                            eventStreamRepository = eventStreamRepository,
                            bucketRepository = bucketRepository,
                        )
                    }
                }.joinAll()
        }
}
