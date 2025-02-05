package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.EventMessage.EventMessageId
import com.kapivara.domain.eventstore.EventStream.EventStreamId
import com.kapivara.domain.eventstore.Identity.IdentityId
import com.kapivara.domain.eventstore.Publisher.PublisherId
import com.kapivara.services.Command
import java.util.UUID
import kotlinx.datetime.Instant

data class StoreEventMessageCommand(
    val id: UUID,
    val identityId: UUID,
    val publisherId: UUID,
    val eventStreamId: UUID,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) : Command