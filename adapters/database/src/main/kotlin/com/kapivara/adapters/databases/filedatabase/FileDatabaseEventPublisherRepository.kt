package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.publishersPath
import com.kapivara.domain.eventstore.Publisher
import com.kapivara.domain.eventstore.ports.EventPublisherRepository
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
