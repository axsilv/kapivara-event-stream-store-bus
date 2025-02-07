package com.kapivara.adapters.databases.filedatabase

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.io.path.Path

class FileDatabase : Database {
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

    override suspend fun writeFileAsync(
        filePath: String,
        content: String,
    ) {
        withContext(Dispatchers.IO) {
            val compressedContent = compressContent(content)

            val path: Path = File(filePath).toPath()

            Files.write(
                path,
                compressedContent,
                StandardOpenOption.CREATE_NEW, // Ensures atomic creation
            )
        }
    }

    override suspend fun lock(key: String) {
        writeFileAsync(
            filePath = "/mutex/$key.txt", // todo
            content = "",
        )
    }

    override suspend fun unlock(key: String) {
        Files.deleteIfExists(Path("/mutex/$key.txt"))
    }

    override suspend fun readFileAsync(filePath: String): String =
        withContext(Dispatchers.IO) {
            val file = File(filePath)

            val compressedContent = file.readBytes()

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
