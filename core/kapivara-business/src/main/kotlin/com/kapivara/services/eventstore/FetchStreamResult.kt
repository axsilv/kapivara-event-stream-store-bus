package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.services.QueryBusinessResult

data class FetchEventStreamBusinessResult(
    val eventMessages: Set<FetchMessageResult>,
) : QueryBusinessResult

fun Stream.toResult() =
    FetchEventStreamBusinessResult(
        eventMessages =
            eventMessages
                .map { it.toResult() }
                .toSet(),
    )
