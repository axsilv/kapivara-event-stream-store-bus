package com.kapivara.services.eventstore

import com.kapivara.services.QueryBusiness
import java.util.UUID

data class FetchEventMessageBusiness(
    val messageId: UUID,
    val eventStreamId: UUID,
) : QueryBusiness
