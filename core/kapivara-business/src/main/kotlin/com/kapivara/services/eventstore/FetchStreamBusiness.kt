package com.kapivara.services.eventstore

import com.kapivara.services.QueryBusiness
import java.util.UUID

data class FetchStreamBusiness(
    val id: UUID,
    val contextName: String,
    val systemName: String,
    val streamType: String,
) : QueryBusiness
