package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.streamPath
import com.kapivara.domain.eventstore.Message
import com.kapivara.domain.eventstore.Message.MessageId
import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import com.kapivara.domain.eventstore.toStreamId
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.util.LinkedHashSet
import java.util.UUID.fromString
import kotlin.io.path.Path

class FileDatabaseEventStorageRepository(
    private val database: FileDatabase,
) : EventStorageRepository {
    override suspend fun append(stream: Stream) {
        stream
            .toMap()
            .toJson()
            .let { content ->
                val path = resourcePath(stream.arrangement())

                database.writeFileAsync(
                    filePath = path,
                    fileName = stream.version().toString(),
                    content = content,
                )
            }
    }

    private fun resourcePath(arrangement: LinkedHashSet<String>): Path =
        streamPath()
            .fold(Path("")) { acc, next -> acc.resolve(next) }
            .let { intermediatePath ->
                arrangement
                    .fold(intermediatePath) { acc, next -> acc.resolve(next) }
            }

    override suspend fun fetch(arrangement: LinkedHashSet<String>): Stream? =
        database
            .readFileAsync(
                filePath = resourcePath(arrangement).toString(),
                fileName = null,
            )?.toMap()
            ?.toStream()

    private fun String.toMap(): Map<String, String> = Json.decodeFromString<Map<String, String>>(this)

    private fun Message.toMap(): Map<String, String> =
        mapOf(
            "id" to id.value.toString(),
            "messageName" to messageName,
            "payloadFormat" to payloadFormat,
            "payloadType" to payloadType,
            "payload" to payload,
            "position" to position.toString(),
            "isFinal" to isFinal.toString(),
            "occurredOn" to occurredOn.toString(),
        )

    private fun Stream.toMap(): Map<String, String> =
        mapOf(
            "id" to id.value.toString(),
            "contextName" to contextName,
            "systemName" to systemName,
            "streamType" to streamType,
            "eventMessages" to
                Json.encodeToString(
                    eventMessages
                        .map { it.toMap() }
                        .toSet(),
                ),
            "createdAt" to createdAt.toString(),
        )

    private fun Map<String, String>.toJson() = Json.encodeToString(this)

    private fun Map<String, Any>.toMessage(): Message =
        Message(
            id = MessageId(fromString(getValue("id") as String)),
            payload = getValue("payload") as String,
            position = (getValue("position") as String).toLong(),
            isFinal = (getValue("isFinal") as String).toBoolean(),
            occurredOn = Instant.parse(getValue("occurredOn") as String),
            messageName = getValue("messageName") as String,
            payloadFormat = getValue("payloadFormat") as String,
            payloadType = getValue("payloadType") as String,
        )

    @Suppress("UNCHECKED_CAST")
    private fun Map<String, String>.toStream(): Stream =
        Stream(
            id =
                fromString(getValue("id"))
                    .toStreamId(),
            contextName = getValue("contextName"),
            systemName = getValue("systemName"),
            streamType = getValue("streamType"),
            eventMessages =
                (
                    Json.decodeFromString<Set<Map<String, String>>>(getValue("eventMessages"))
                ).map { it.toMessage() }
                    .toSet(),
            createdAt = getValue("createdAt").toLong(),
        )
}
