package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.ports.PublisherRepository
import com.kapivara.services.QueryBusinessHandler

class FetchPublisherBusinessHandler(
    private val publisherRepository: PublisherRepository,
) : QueryBusinessHandler<FetchPublisherBusiness, FetchPublisherBusinessResult> {
    override suspend fun fetch(queryBusiness: FetchPublisherBusiness): FetchPublisherBusinessResult? {
        TODO("Not yet implemented")
    }
}
