package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.Database.Key
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.io.path.Path
import kotlin.io.path.readBytes

class FileDatabase : Database {
    companion object {
        private val mutex: Mutex = Mutex()
    }

    private fun compressContent(content: String): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        GZIPOutputStream(byteArrayOutputStream).use { gzipOutputStream ->
            gzipOutputStream.write(content.toByteArray())
        }
        return byteArrayOutputStream.toByteArray()
    }

    private fun decompressContent(compressedContent: ByteArray): String {
        val byteArrayInputStream = ByteArrayInputStream(compressedContent)
        GZIPInputStream(byteArrayInputStream).use { gzipInputStream ->
            return gzipInputStream.readBytes().toString(Charsets.UTF_8)
        }
    }

    override suspend fun writeFileAsync(
        filePath: Path,
        fileName: String,
        content: String,
    ) {
        mutex.withLock(filePath) {
            withContext(Dispatchers.IO) {
                val compressedContent = compressContent(content)

                Files.createDirectories(filePath)
                val fullPath = filePath.resolve("$fileName.txt")
                Files.write(
                    fullPath,
                    compressedContent,
                )
            }
        }
    }

    override suspend fun lock(key: String): Key {
        val key = Key(UUID.randomUUID().toString())
        writeFileAsync(
            filePath = Path.of("", "mutex"), // todo
            fileName = "$key",
            content = key.value,
        )

        return key
    }

    override suspend fun unlock(key: String) {
        Files.deleteIfExists(Path("/mutex/$key.txt"))
    }

    override suspend fun readFileAsync(
        filePath: String,
        fileName: String?,
    ): String? =
        withContext(Dispatchers.IO) {
            val compressedContent =
                if (fileName == null) {
                    File(filePath)
                        .listFiles()
                        .mapNotNull { file ->
                            file
                                .nameWithoutExtension
                                .toIntOrNull()
                                ?.let { number -> number to file.toPath() }
                        }.maxBy { it.first }
                        .second
                        .readBytes()
                } else {
                    val file = File(filePath + fileName)

                    file.readBytes()
                }

            decompressContent(compressedContent)
        }

    override suspend fun readAllFilesAsync(filePath: String): Set<String> =
        withContext(Dispatchers.IO) {
            val folder = File(filePath)
            folder
                .listFiles()
                .toList()
                .map { file ->
                    val compressedContent = file.readBytes()
                    decompressContent(compressedContent)
                }.toSet()
        }
}
