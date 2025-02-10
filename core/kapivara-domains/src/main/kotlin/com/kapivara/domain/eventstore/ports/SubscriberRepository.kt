package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.Subscriber

fun interface SubscriberRepository {
    suspend fun store(subscriber: Subscriber)
}
