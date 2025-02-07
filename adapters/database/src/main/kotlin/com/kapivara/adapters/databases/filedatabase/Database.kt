package com.kapivara.adapters.databases.filedatabase

interface Database {
    suspend fun writeFileAsync(
        filePath: String,
        content: String,
    )

    suspend fun lock(key: String)

    suspend fun unlock(key: String)

    suspend fun readFileAsync(filePath: String): String

    suspend fun readAllFilesAsync(filePath: String): Set<String>
}
