package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.ports.StreamRepository
import com.kapivara.domain.eventstore.toEventStreamId
import com.kapivara.services.QueryBusinessHandler

class FetchEventStreamBusinessHandler(
    private val streamRepository: StreamRepository,
) : QueryBusinessHandler<FetchEventStreamBusiness, FetchEventStreamBusinessResult> {
    override suspend fun fetch(queryBusiness: FetchEventStreamBusiness): FetchEventStreamBusinessResult? =
        Stream
            .fetch(
                streamId = queryBusiness.eventStreamId.toEventStreamId(),
                streamRepository = streamRepository,
            )?.toResult()
}
