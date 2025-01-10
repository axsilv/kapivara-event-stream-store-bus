package com.eventhub.ports.eventbus

import kotlinx.coroutines.Deferred

interface EventBusClient {
    suspend fun send(): Deferred<Unit>
}
