package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.services.QueryBusinessResult

data class FetchEventStreamBusinessResult(
    val events: Set<FetchEventResult>,
) : QueryBusinessResult

fun Stream.toResult() =
    FetchEventStreamBusinessResult(
        events =
            events
                .map { it.toResult() }
                .toSet(),
    )
