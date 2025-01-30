package com.eventhub.domain.eventstore.ports

import com.eventhub.domain.eventstore.EventSubscriber

fun interface EventSubscriberRepository {
    suspend fun store(eventSubscriber: EventSubscriber)
}
