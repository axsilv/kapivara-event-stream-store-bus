package com.kapivara.adapters.databases.filedatabase

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.minutes

object DatabaseCache {
    private lateinit var cache: ConcurrentHashMap<*, *>
    private lateinit var expires: ConcurrentHashMap<*, Instant>

    fun <K, V> create(): DatabaseCache {
        cache = ConcurrentHashMap<K, V>()
        expires = ConcurrentHashMap<K, Instant>()
        return this
    }

    fun <K, V> store(
        key: K,
        value: V,
        expiresIn: Int = 10,
    ) = expires.plus(key to Clock.System.now().plus(expiresIn.minutes)).let {
        cache.plus(key to value)
    }

    @Suppress("UNCHECKED_CAST")
    fun <K, V> fetch(key: K): V? = cache[key] as? V?
}
