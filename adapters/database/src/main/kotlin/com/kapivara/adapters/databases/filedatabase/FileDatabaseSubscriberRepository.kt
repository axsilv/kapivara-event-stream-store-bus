package com.kapivara.adapters.databases.filedatabase

import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.subscribersPath
import com.kapivara.domain.eventbus.EventBusBucket.EventBusBucketId
import com.kapivara.domain.eventstore.Identity.IdentityId
import com.kapivara.domain.eventstore.Subscriber
import com.kapivara.domain.eventstore.ports.EventSubscriberRepository
import kotlinx.serialization.json.Json

class FileDatabaseSubscriberRepository(
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
