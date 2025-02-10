package com.kapivara.services.eventstore

import com.kapivara.services.QueryBusiness

data class FetchPublisherBusiness(
    val publisherId: Long,
) : QueryBusiness
