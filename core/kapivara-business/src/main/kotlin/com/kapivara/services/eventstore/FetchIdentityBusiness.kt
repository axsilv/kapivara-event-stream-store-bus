package com.kapivara.services.eventstore

import com.kapivara.services.QueryBusiness

data class FetchIdentityBusiness(
    val identityId: Long,
) : QueryBusiness
