package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import com.eventhub.domain.eventstore.EventData.RelatedIdentifier.RelatedIdentifierId
import kotlinx.serialization.json.JsonElement
import java.util.UUID

data class EventData(
    val owner: String,
    val type: String,
    val alias: String,
    val relatedIdentifiers: List<RelatedIdentifier>,
    val data: JsonElement,
) {
    data class RelatedIdentifier(
        val relatedIdentifierId: RelatedIdentifierId,
        val owner: String,
    ) {
        data class RelatedIdentifierId(
            val value: UUID,
        ) : Identifier(value = value)
    }
}

fun UUID.toRelatedIdentifierId() = RelatedIdentifierId(value = this)
