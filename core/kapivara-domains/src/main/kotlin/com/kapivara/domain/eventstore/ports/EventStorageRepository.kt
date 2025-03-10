package com.kapivara.domain.eventstore.ports

import com.kapivara.domain.eventstore.Stream
import java.util.LinkedHashSet

interface EventStorageRepository {
    suspend fun append(stream: Stream)

    suspend fun fetchLast(arrangement: LinkedHashSet<String>): Stream?
}
