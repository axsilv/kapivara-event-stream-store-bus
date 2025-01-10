package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.Event
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.EventStoreRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class AddAllEventsService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusClient: EventBusClient,
) {
    suspend fun addAll(events: List<Event>) =
        coroutineScope {
            events
                .map { event ->
                    launch {
                        event.add(
                            eventStoreRepository = eventStoreRepository,
                            eventBusClient = eventBusClient,
                        )
                    }
                }.joinAll()
        }
}
