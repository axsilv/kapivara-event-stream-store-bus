package com.kapivara.adapters.databases.filedatabase

import java.util.concurrent.ConcurrentHashMap

object DatabaseCache {

    private lateinit var cache: ConcurrentHashMap<*, *>

    fun <K, V> create() { cache = ConcurrentHashMap<K, V>() }

    fun <K, V> store(key: K, value: V) = cache.plus(key to value)

    @Suppress("UNCHECKED_CAST")
    fun <K, V> fetch(key: K): V = cache[key] as V
}