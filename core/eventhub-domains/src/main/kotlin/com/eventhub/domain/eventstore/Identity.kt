package com.eventhub.domain.eventstore

import com.eventhub.domain.Identifier
import java.util.UUID

data class Identity(
    val identityId: IdentityId,
    val eventName: String,
    val systemOriginName: String,
    val metadata: Map<String, String>,
) {
    data class IdentityId(
        private val value: UUID,
    ) : Identifier(value = value)
}
