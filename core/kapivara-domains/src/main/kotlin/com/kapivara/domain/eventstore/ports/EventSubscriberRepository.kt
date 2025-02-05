package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.Subscriber

fun interface EventSubscriberRepository {
    suspend fun store(subscriber: Subscriber)
}
