package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import com.kapivara.domain.eventstore.toStreamId
import com.kapivara.services.QueryBusinessHandler

class FetchStreamHandler(
    private val eventStorageRepository: EventStorageRepository,
) : QueryBusinessHandler<FetchStream, FetchEventStreamBusinessResult> {
    override suspend fun fetch(queryBusiness: FetchStream): FetchEventStreamBusinessResult? =
        Stream
            .fetch(
                id = queryBusiness.id.toStreamId(),
                contextName = queryBusiness.contextName,
                systemName = queryBusiness.systemName,
                streamType = queryBusiness.streamType,
                eventStorageRepository = eventStorageRepository,
            )?.toResult()
}
