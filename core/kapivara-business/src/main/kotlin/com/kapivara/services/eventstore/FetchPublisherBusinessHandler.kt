package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Publisher
import com.kapivara.domain.eventstore.ports.PublisherRepository
import com.kapivara.domain.eventstore.toPublisherId
import com.kapivara.services.QueryBusinessHandler

class FetchPublisherBusinessHandler(
    private val publisherRepository: PublisherRepository,
) : QueryBusinessHandler<FetchPublisherBusiness, FetchPublisherBusinessResult> {
    override suspend fun fetch(queryBusiness: FetchPublisherBusiness): FetchPublisherBusinessResult? =
        Publisher
            .fetch(
                id = queryBusiness.publisherId.toPublisherId(),
                publisherRepository = publisherRepository,
            )?.toResult()

    private fun Publisher.toResult() =
        FetchPublisherBusinessResult(
            id = id.value,
            publisherName = publisherName,
        )
}
