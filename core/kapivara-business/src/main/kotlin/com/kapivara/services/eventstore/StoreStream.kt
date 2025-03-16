package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.toStreamId
import com.kapivara.services.CommandBusiness
import kotlinx.datetime.Clock.System.now
import java.util.UUID

data class StoreStream(
    val id: UUID,
    val contextName: String = "defaultContextName",
    val systemName: String = "defaultSystemName",
    val streamType: String = "defaultStreamType",
    val events: Set<StoreEvent>,
) : CommandBusiness {
    fun toStream(): Stream =
        Stream(
            id = id.toStreamId(),
            contextName = contextName,
            systemName = systemName,
            streamType = streamType,
            events =
                events
                    .map {
                        it.toEvent()
                    }.toSet(),
            createdAt = now().toEpochMilliseconds(),
        )
}
