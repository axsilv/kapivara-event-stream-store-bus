package com.kapivara.services.eventstore

import com.kapivara.services.QueryBusiness
import java.util.UUID

data class FetchEventStreamBusiness(
    val eventStreamId: UUID,
) : QueryBusiness
