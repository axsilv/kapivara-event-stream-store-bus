package com.kapivara.adapters.databases.filedatabase

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.minutes

@OptIn(DelicateCoroutinesApi::class)
class DatabaseCache {
    private lateinit var cache: ConcurrentHashMap<*, *>
    private lateinit var expires: ConcurrentHashMap<*, Instant>
    private val log = KotlinLogging.logger { }

    fun <K, V> create(): DatabaseCache {
        cache = ConcurrentHashMap<K, V>()
        expires = ConcurrentHashMap<K, Instant>()

        GlobalScope.launch {
            expireCache()
        }

        return this
    }

    fun <K, V> store(
        key: K,
        value: V,
        expiresIn: Int? = 10,
    ) {
        if (expiresIn != null) {
            expires.plus(key to Clock.System.now().plus(expiresIn.minutes))
        }

        cache.plus(key to value)
    }

    @Suppress("UNCHECKED_CAST")
    fun <K, V> fetch(key: K): V? = cache[key] as? V?

    private fun expireCache() {
        runCatching {
            while (true) {
                try {
                    expires.map { (key, expireInstant) ->
                        if (expireInstant > Clock.System.now()) {
                            cache.remove(key)
                            expires.remove(key)
                        }
                    }
                } catch (e: Exception) {
                    log.error { "Cache error ${e.localizedMessage}" }
                } finally {
                    log.debug { "Cache expiration loop finished" }
                }
            }
        }

        log.error { "Something went wrong, restarting expire cache routine" }

        expireCache()
    }
}
