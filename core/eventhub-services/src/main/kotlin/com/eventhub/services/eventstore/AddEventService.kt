package com.eventhub.services.eventstore

import com.eventhub.domain.eventstore.toEvent
import com.eventhub.ports.eventbus.EventBusClient
import com.eventhub.ports.eventstore.AddEvent
import com.eventhub.ports.eventstore.EventCommandService
import com.eventhub.ports.eventstore.EventStoreRepository

class AddEventService(
    private val eventStoreRepository: EventStoreRepository,
    private val eventBusClient: EventBusClient,
) : EventCommandService {
    override suspend fun add(addEvent: AddEvent) =
        addEvent.toEvent().add(
            eventStoreRepository = eventStoreRepository,
            eventBusClient = eventBusClient,
        )
}
