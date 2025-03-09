package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.Database.Key
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files.createDirectories
import java.nio.file.Files.deleteIfExists
import java.nio.file.Files.write
import java.nio.file.Path
import java.util.UUID.randomUUID
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.io.path.Path
import kotlin.io.path.readBytes
import kotlin.text.Charsets.UTF_8

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

    private fun decompressContent(compressedContent: ByteArray): String =
        ByteArrayInputStream(compressedContent)
            .gzipInputStream()
            .readBytesToString()

    private fun ByteArrayInputStream.gzipInputStream() = GZIPInputStream(this)

    private fun GZIPInputStream.readBytesToString() = readBytes().toString(UTF_8)

    override suspend fun writeFileAsync(
        filePath: Path,
        fileName: String,
        content: String,
    ): Unit =
        mutex.withLock(filePath) {
            withContext(IO) {
                val compressedContent = compressContent(content)
                val fullPath = filePath.resolve("$fileName.txt")

                createDirectories(filePath)

                write(
                    fullPath,
                    compressedContent,
                )
            }
        }

    override suspend fun lock(key: String): Key =
        Key(randomUUID().toString()).let { key ->
            writeFileAsync(
                filePath = Path.of("", "mutex"), // todo
                fileName = "$key",
                content = key.value,
            )

            key
        }

    override suspend fun unlock(key: String) {
        deleteIfExists(Path("/mutex/$key.txt"))
    }

    override suspend fun readFileAsync(
        filePath: String,
        fileName: String?,
    ): String? =
        withContext(IO) {
            val compressedContent =
                if (fileName == null) {
                    readLatestFile(filePath)
                } else {
                    val file = File(filePath + fileName)

                    file.readBytes()
                }

            decompressContent(compressedContent)
        }

    private fun readLatestFile(filePath: String): ByteArray =
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

    override suspend fun readAllFilesAsync(filePath: String): Set<String> =
        withContext(IO) {
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
