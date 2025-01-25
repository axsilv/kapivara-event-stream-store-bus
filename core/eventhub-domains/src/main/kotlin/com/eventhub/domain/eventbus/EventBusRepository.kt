package com.eventhub.domain.eventbus

interface EventBusRepository {
    suspend fun store()
}
