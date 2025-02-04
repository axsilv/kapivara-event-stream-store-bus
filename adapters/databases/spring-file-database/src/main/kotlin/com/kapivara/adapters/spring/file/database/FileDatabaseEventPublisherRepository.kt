package com.kapivara.adapters.spring.file.database

import com.eventhub.domain.eventstore.Publisher
import com.eventhub.domain.eventstore.ports.EventPublisherRepository
import com.kapivara.adapters.spring.file.database.FileDatabaseVariables.publishersPath
import kotlinx.serialization.json.Json

class FileDatabaseEventPublisherRepository(
    private val database: FileDatabase,
) : EventPublisherRepository {
    override suspend fun store(publisher: Publisher) {
        val longId = publisher.id.value
        val jsonContent = Json.encodeToString(publisher.toMap())

        database.writeFileAsync(
            filePath = "${publishersPath()}/$longId",
            content = jsonContent,
        )
    }

    private fun Publisher.toMap(): Map<String, Any> =
        mapOf(
            "id" to id.value,
            "publisherName" to publisherName,
        )
}
