package com.kapivara.services.eventstore

import com.kapivara.services.QueryBusinessResult
import java.util.UUID

data class FetchSubscriberBusinessResult(
    val id: Long,
    val description: String,
    val systemNameOrigin: String,
    val listening: List<Long>,
    val eventBusBuckets: List<UUID>,
) : QueryBusinessResult
