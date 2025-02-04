package com.kapivara.domain.eventstore.ports

import com.eventhub.domain.eventstore.Subscriber

fun interface EventSubscriberRepository {
    suspend fun store(subscriber: Subscriber)
}
