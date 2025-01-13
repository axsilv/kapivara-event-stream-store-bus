package com.eventhub.ports.eventstore

interface EventsCommandService {
    suspend fun addAll(addEvents: List<AddEvent>)
}
