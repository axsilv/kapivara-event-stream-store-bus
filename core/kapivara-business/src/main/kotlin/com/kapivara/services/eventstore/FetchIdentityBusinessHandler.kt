package com.kapivara.services.eventstore

import com.kapivara.domain.eventstore.Identity
import com.kapivara.domain.eventstore.ports.IdentityRepository
import com.kapivara.domain.eventstore.toIdentityId
import com.kapivara.services.QueryBusinessHandler

class FetchIdentityBusinessHandler(
    private val identityRepository: IdentityRepository,
) : QueryBusinessHandler<FetchIdentityBusiness, FetchIdentityBusinessResult> {
    override suspend fun fetch(queryBusiness: FetchIdentityBusiness): FetchIdentityBusinessResult? =
        Identity
            .fetch(
                identityRepository = identityRepository,
                identityId = queryBusiness.identityId.toIdentityId(),
            )?.toResult()
}
