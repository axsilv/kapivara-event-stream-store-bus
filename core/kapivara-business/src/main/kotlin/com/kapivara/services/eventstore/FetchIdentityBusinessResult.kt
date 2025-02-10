package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Identity
import com.kapivara.services.QueryBusinessResult

data class FetchIdentityBusinessResult(
    val id: Long,
    val name: String,
    val publisherId: Long,
    val eventSubscribers: Set<Long>,
    val metadata: Map<String, String>,
) : QueryBusinessResult

fun Identity.toResult() =
    FetchIdentityBusinessResult(
        id = id.value,
        name = name,
        publisherId = publisherId.value,
        eventSubscribers =
            eventSubscribers
                .map { it.value }
                .toSet(),
        metadata = metadata,
    )
