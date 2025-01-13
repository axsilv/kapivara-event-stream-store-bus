package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.toEvent
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.AddEvent
import com.eventhub.ports.eventstore.EventStoreRepository
import com.eventhub.ports.eventstore.EventsCommandService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class AddAllEventsService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusClient: EventBusClient,
) : EventsCommandService {
    override suspend fun addAll(addEvents: List<AddEvent>) =
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
