package com.kapivara.eventhub.adapters.databases.r2dbc.postgresql

import com.eventhub.domain.mutex.ports.MutexRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository
import kotlin.time.Duration.Companion.seconds

@Repository
class R2dbcMutexRepository(
    private val db: DatabaseClient,
) : MutexRepository {
    companion object {
        private val mutex: Mutex = Mutex()
    }

    override suspend fun lock(lock: String) {
        mutex.withLock(lock) {
            val expiresAt =
                Clock.System
                    .now()
                    .plus(60.seconds)
                    .toJavaInstant()
            db
                .sql(
                    """
            INSERT INTO mutexes (lock, expires_at)
            VALUES (:lock, :expiresAt)
            ON CONFLICT (lock) DO NOTHING
        """,
                ).bind("lock", lock)
                .bind("expiresAt", expiresAt)
                .await()
        }
    }

    override suspend fun unlock(lock: String) {
        mutex.withLock {
            db
                .sql("DELETE FROM mutexes WHERE lock = :lock")
                .bind("lock", lock)
        }
    }

    override suspend fun cleanupExpiredLocks() {
        mutex.withLock {
            db
                .sql("DELETE FROM mutexes WHERE expires_at < NOW()")
                .await()
        }
    }
}
