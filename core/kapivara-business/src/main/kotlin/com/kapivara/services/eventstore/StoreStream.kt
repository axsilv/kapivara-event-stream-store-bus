package com.kapivara.services.eventstore

import com.kapivara.services.CommandBusiness
import java.util.UUID

data class StoreStream(
    val id: UUID,
    val contextName: String?,
    val systemName: String?,
    val streamType: String?,
) : CommandBusiness
