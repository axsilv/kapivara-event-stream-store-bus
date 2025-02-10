package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.streamPath
import com.kapivara.domain.eventstore.Identity.IdentityId
import com.kapivara.domain.eventstore.Message
import com.kapivara.domain.eventstore.Message.MessageId
import com.kapivara.domain.eventstore.Publisher.PublisherId
import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.Stream.StreamId
import com.kapivara.domain.eventstore.ports.StreamRepository
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import java.io.File
import java.util.UUID

class FileDatabaseStreamRepository(
    private val database: FileDatabase,
) : StreamRepository {
    private val cache = EventStreamCache

    override suspend fun store(
        message: Message,
        useCache: Boolean,
    ) {
        val streamId = message.streamId.toString()
        val streamRange = number<UUID>(message.streamId.value)
        val messageId = message.id.toString()
        val folderStreamHash = streamId.hashCode()
        val streamFile = File(streamPath() + "$streamRange/$folderStreamHash/$streamId/$messageId.gz")

        streamFile.parentFile?.mkdirs()

        database.writeFileAsync(
            filePath = streamFile.path,
            content = Json.encodeToString(message.toMap()),
        )

        if (useCache) {
            cache
                .fetchFromCache(message.streamId)
                ?.plus(message)
                ?.also { streamCache ->
                    cache.store(
                        key = message.streamId.value,
                        value = streamCache,
                    )
                }
        }
    }

    override suspend fun fetch(
        streamId: StreamId,
        useCache: Boolean,
    ): Stream? {
        if (useCache) {
            cache
                .fetchFromCache(streamId)
                ?.let {
                    return Stream(eventMessages = it)
                }
        }
        val streamRange = number<UUID>(streamId.value)
        val folderStreamHash = streamId.toString().hashCode()
        val streamFile = File(streamPath() + "$streamRange/$folderStreamHash/$streamId")

        if (streamFile.exists().not()) return null

        val messages = fetchStreamFromStore(streamFile)

        return Stream(eventMessages = messages)
    }

    private suspend fun FileDatabaseStreamRepository.fetchStreamFromStore(streamFile: File): Set<Message> =
        streamFile
            .listFiles()
            .toList()
            .map { entry -> entry.toMap() }
            .map { jsonEntry -> jsonEntry.toEventMessage() }
            .toSet()

    private suspend fun File.toMap(): Map<String, Any> = Json.decodeFromString<Map<String, Any>>(database.readFileAsync(this.path))

    private fun Message.toMap(): Map<String, Any> =
        mapOf(
            "id" to id.value,
            "identityId" to identityId.value,
            "publisherId" to publisherId.value,
            "eventStreamId" to streamId.value,
            "payload" to payload,
            "position" to position,
            "isFinal" to isFinal,
            "occurredOn" to occurredOn.toString(),
        )

    private fun Map<String, Any>.toEventMessage(): Message =
        Message(
            id = MessageId(getValue("id") as UUID),
            identityId = IdentityId(getValue("identityId") as Long),
            publisherId = PublisherId(getValue("publisherId") as Long),
            streamId = StreamId(getValue("eventStreamId") as UUID),
            payload = getValue("payload") as String,
            position = (getValue("position") as Number).toLong(),
            isFinal = getValue("isFinal") as Boolean,
            occurredOn = Instant.parse(getValue("occurredOn") as String),
        )
}
