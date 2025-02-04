package com.kapivara.adapters.databases.filedatabase

import com.eventhub.domain.eventstore.Identity
import com.eventhub.domain.eventstore.Identity.IdentityId
import com.eventhub.domain.eventstore.Publisher.PublisherId
import com.eventhub.domain.eventstore.Subscriber.SubscriberId
import com.eventhub.domain.eventstore.ports.EventIdentityRepository
import com.kapivara.adapters.databases.filedatabase.FileDatabaseVariables.identitiesPath
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class FileDatabaseEventIdentityRepository(
    private val database: FileDatabase,
) : EventIdentityRepository {
    override suspend fun store(identity: Identity) {
        val jsonContent =
            identity
                .toMap()
                .toJson()

        val identityFolder = "${identitiesPath()}/${identity.id.value}"
        if (Files.exists(Path(identityFolder)).not()) {
            File(identityFolder).mkdirs()
        }

        database.writeFileAsync(
            filePath = "$identityFolder/identity.gz",
            content = jsonContent,
        )
    }

    override suspend fun fetchPublisherId(identityId: IdentityId): PublisherId? {
        val jsonContent = database.readFileAsync(filePath = "${identitiesPath()}/${identityId.value})")
        val contentMap = Json.decodeFromString<Map<String, Any>>(jsonContent)

        return PublisherId(contentMap["publisherId"] as Long)
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun fetchSubscribersId(identityId: IdentityId): Set<SubscriberId> =
        database
            .readAllFilesAsync(filePath = identitySubscribersPath(identityId))
            .map { SubscriberId(it.toLong()) }
            .toSet()

    @Suppress("UNCHECKED_CAST")
    override suspend fun appendSubscriberId(
        identityId: IdentityId,
        subscriberId: SubscriberId,
    ) {
        val identitySubscribersFolder = identitySubscribersPath(identityId)
        if (Files.exists(Path(identitySubscribersFolder)).not()) {
            File(identitySubscribersFolder).mkdirs()
        }

        database.writeFileAsync(
            filePath = "$identitySubscribersFolder/${subscriberId.value}.gz",
            content = subscriberId.toString(),
        )
    }

    private fun identitySubscribersPath(identityId: IdentityId): String = "${identitiesPath()}/${identityId.value}/subscribers"

    private fun Identity.toMap(): Map<String, Any> =
        mapOf(
            "id" to id.value,
            "name" to name,
            "publisherId" to publisherId,
            "metadata" to metadata,
        )

    private fun Map<String, Any>.toJson() = Json.encodeToString(this)
}
