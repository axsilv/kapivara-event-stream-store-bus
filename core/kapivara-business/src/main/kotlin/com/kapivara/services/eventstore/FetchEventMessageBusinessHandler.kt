package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Stream
import com.kapivara.domain.eventstore.ports.StreamRepository
import com.kapivara.domain.eventstore.toEventMessageId
import com.kapivara.domain.eventstore.toEventStreamId
import com.kapivara.services.QueryBusinessHandler

class FetchEventMessageBusinessHandler(
    private val streamRepository: StreamRepository,
) : QueryBusinessHandler<FetchEventMessageBusiness, FetchEventMessageBusinessResult> {
    override suspend fun fetch(queryBusiness: FetchEventMessageBusiness): FetchEventMessageBusinessResult? {
        val messageId = queryBusiness.messageId.toEventMessageId()
        val eventStreamId = queryBusiness.eventStreamId.toEventStreamId()

        return Stream
            .fetch(
                streamId = eventStreamId,
                messageId = messageId,
                streamRepository = streamRepository,
            )?.toResult()
    }
}
