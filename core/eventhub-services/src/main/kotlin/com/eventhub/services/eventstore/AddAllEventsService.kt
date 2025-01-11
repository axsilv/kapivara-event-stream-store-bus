package com.eventhub.services.eventstore

import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.EventStoreRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class AddAllEventsService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusClient: EventBusClient,
) {
    suspend fun addAll(addEvents: List<AddEvent>) =
        coroutineScope {
            addEvents
                .map { addEvent ->
                    launch {
                        addEvent.toEvent().add(
                            eventStoreRepository = eventStoreRepository,
                            eventBusClient = eventBusClient,
                        )
                    }
                }.joinAll()
        }
}
