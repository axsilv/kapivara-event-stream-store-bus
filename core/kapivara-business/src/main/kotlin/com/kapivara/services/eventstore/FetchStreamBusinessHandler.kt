package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.ports.EventStorageRepository
import com.kapivara.domain.eventstore.toStreamId
import com.kapivara.services.QueryBusinessHandler

class FetchStreamBusinessHandler(
    private val eventStorageRepository: EventStorageRepository,
) : QueryBusinessHandler<FetchStreamBusiness, FetchEventStreamBusinessResult> {
    override suspend fun fetch(queryBusiness: FetchStreamBusiness): FetchEventStreamBusinessResult? =
        Stream
            .fetch(
                streamId = queryBusiness.id.toStreamId(),
                eventStorageRepository = eventStorageRepository,
            )?.toResult()
}
