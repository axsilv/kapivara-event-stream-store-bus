package com.kapivara.adapters.databases.filedatabase

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.channels.FileLock
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class FileDatabase {
    companion object {
        private val log = KotlinLogging.logger { }
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

    suspend fun writeFileAsync(
        filePath: String,
        content: String,
    ) = withContext(Dispatchers.IO) {
        val file = File(filePath)

        if (file.exists().not()) {
            file.createNewFile()

            val compressedContent = compressContent(content)

            RandomAccessFile(file, "rw").use { raf ->
                val channel: FileChannel = raf.channel

                val lock: FileLock = channel.lock()
                try {
                    raf.write(compressedContent)
                    log.info { "Compressed file written successfully: $filePath" }
                } finally {
                    lock.release()
                    log.info { "File lock released" }
                }
            }
        }
    }

    suspend fun readFileAsync(filePath: String): String =
        withContext(Dispatchers.IO) {
            val file = File(filePath)

            val compressedContent = file.readBytes()

            decompressContent(compressedContent)
        }

    suspend fun readAllFilesAsync(filePath: String): Set<String> =
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
