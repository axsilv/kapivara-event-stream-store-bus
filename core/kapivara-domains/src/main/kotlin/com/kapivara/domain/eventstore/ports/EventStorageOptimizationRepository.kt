package com.kapivara.domain.eventstore.ports

interface EventStorageOptimizationRepository {
    suspend fun store(arrangement: LinkedHashSet<String>)

    suspend fun fetchAll(): Set<String>

    suspend fun remove(arrangement: String)
}
