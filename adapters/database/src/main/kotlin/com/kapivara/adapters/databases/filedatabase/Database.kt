package com.kapivara.adapters.databases.filedatabase

import java.nio.file.Path

interface Database {
    suspend fun writeFileAsync(
        filePath: Path,
        fileName: String,
        content: String,
    )

    suspend fun lock(key: String): Key

    suspend fun unlock(key: String)

    suspend fun readFileAsync(
        filePath: String,
        fileName: String?,
    ): String?

    suspend fun readAllFilesAsync(filePath: String): Set<String>

    data class Key(
        val value: String,
    )
}
