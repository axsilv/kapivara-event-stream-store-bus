package com.kapivara.adapters.spring.file.database

import com.eventhub.domain.eventbus.EventBusBucket.EventBusBucketId
import com.eventhub.domain.eventstore.Identity.IdentityId
import com.eventhub.domain.eventstore.Subscriber
import com.eventhub.domain.eventstore.ports.EventSubscriberRepository
import com.kapivara.adapters.spring.file.database.FileDatabaseVariables.subscribersPath
import kotlinx.serialization.json.Json

class FileDatabaseEventSubscriberRepository(
    private val database: FileDatabase,
) : EventSubscriberRepository {
    override suspend fun store(subscriber: Subscriber) {
        val jsonContent = Json.encodeToString(subscriber.toMap())

        database.writeFileAsync(
            filePath = "${subscribersPath()}/${subscriber.id.value}",
            content = jsonContent,
        )
    }

    private fun Subscriber.toMap(): Map<String, Any> =
        mapOf(
            "id" to id.value,
            "description" to description,
            "systemNameOrigin" to systemNameOrigin,
            "listening" to listening.toJson(),
            "eventBusBuckets" to eventBusBuckets.toJson(),
        )

    private fun List<IdentityId>.toJson(): String = Json.encodeToString(this.map { it.value })

    private fun List<EventBusBucketId>.toJson(): String = Json.encodeToString(this.map { it.value })
}
