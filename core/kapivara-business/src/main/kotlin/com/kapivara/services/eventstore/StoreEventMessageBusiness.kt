package com.kapivara.services.eventstore

import com.kapivara.services.CommandBusiness
import kotlinx.datetime.Instant
import java.util.UUID

data class StoreEventMessageBusiness(
    val id: UUID,
    val identityId: Long,
    val publisherId: Long,
    val eventStreamId: UUID,
    val payload: String,
    val position: Long,
    val isFinal: Boolean,
    val occurredOn: Instant,
) : CommandBusiness
