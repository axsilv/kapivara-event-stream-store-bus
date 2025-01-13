package com.eventhub.ports.eventstore

interface EventCommandService {
    suspend fun add(addEvent: AddEvent)
}
