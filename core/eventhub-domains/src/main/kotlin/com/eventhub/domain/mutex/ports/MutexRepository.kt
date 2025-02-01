package com.eventhub.domain.mutex.ports

interface MutexRepository {
    suspend fun lock(lock: String)

    suspend fun unlock(lock: String)

    suspend fun cleanupExpiredLocks()
}
